package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dto.Post
import repository.PostRepository
import repository.PostRepositoryFileImplementation

private val empty = Post(
    id = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImplementation(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun repostById(id: Long) = repository.repostById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun commentById(id: Long) = repository.commentById(id)
}