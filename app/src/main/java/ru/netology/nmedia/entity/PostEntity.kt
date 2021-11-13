package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Long,
    val comments: Long,
    val reposts: Long,
    val views: Long,
    val video: String,
    val likedByMe: Boolean,
    val repostedByMe: Boolean,
    val commentedByMe: Boolean
) {
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        likes,
        comments,
        reposts,
        views,
        video,
        likedByMe,
        repostedByMe,
        commentedByMe
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.content,
                dto.published,
                dto.likes,
                dto.comments,
                dto.reposts,
                dto.views,
                dto.video,
                dto.likedByMe,
                dto.repostedByMe,
                dto.commentedByMe
            )

    }
}

