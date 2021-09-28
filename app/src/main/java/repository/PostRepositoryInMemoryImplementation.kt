package repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dto.Post

class PostRepositoryInMemoryImplementation : PostRepository {

    companion object {
        private var defaultPost = Post(
            author = "Сергей",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb\"\n",
            published = "26 сентября в 13:57",
            likes = 999,
            comments = 10L,
            reposts = 56L,
            views = 20500L,
            likedByMe = false
        )
    }

    private val data = MutableLiveData(defaultPost)

    override fun get(): LiveData<Post> = data

    override fun like() {
        val currentPost = data.value ?: return
        val currentLikes = if (!currentPost.likedByMe) currentPost.likes+1 else currentPost.likes-1
        data.value = currentPost.copy(likedByMe = !currentPost.likedByMe, likes = currentLikes)
    }

    override fun repost() {
        val currentPost = data.value ?: return
        val currentReposts = currentPost.reposts+1
        data.value = currentPost.copy(reposts = currentReposts)
    }
}