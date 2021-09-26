package ru.netology.nmedia

data class Post(
    val id: Long = 0L,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    var likes: Long = 0L,
    var comments: Long = 0L,
    var reposts: Long = 0L,
    var views: Long = 0L,
    var likedByMe: Boolean = false
)