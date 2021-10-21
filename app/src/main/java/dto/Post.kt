package dto

data class Post(
    val id: Long = 0L,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val likes: Long = 0L,
    val comments: Long = 0L,
    val reposts: Long = 0L,
    val views: Long = 0L,
    val video: String = "",
    val likedByMe: Boolean = false,
    val repostedByMe: Boolean = false,
    val commentedByMe: Boolean = false
)