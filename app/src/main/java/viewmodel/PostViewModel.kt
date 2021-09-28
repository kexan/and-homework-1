package viewmodel

import androidx.lifecycle.ViewModel
import repository.PostRepository
import repository.PostRepositoryInMemoryImplementation

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImplementation()
    val data = repository.get()
    fun like() = repository.like()
    fun repost() = repository.repost()
}