package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImplementation(
    private val context: Context
) : PostRepository {

    companion object {
        private val gson = Gson()
        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
        private const val filename = "posts.json"
        private var nextId = 1L
        private var posts = emptyList<Post>()
        private val data = MutableLiveData(posts)
    }

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }


    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1
            )
        }
        data.value = posts
        sync()
    }

    override fun repostById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                repostedByMe = true,
                reposts = it.reposts + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun commentById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                commentedByMe = !it.commentedByMe,
                comments = if (!it.commentedByMe) it.comments + 1 else it.comments - 1
            )
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun findById(id: Long): Post? {
        return posts.find { it.id == id }
    }


    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }

        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}