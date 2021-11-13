package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            Post(
                it.id,
                it.author,
                it.content,
                it.published,
                it.likes,
                it.comments,
                it.reposts,
                it.views,
                it.video,
                it.likedByMe,
                it.repostedByMe,
                it.commentedByMe
            )
        }
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun repostById(id: Long) {
        dao.repostById(id)
    }

    override fun commentById(id: Long) {
        dao.commentById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun findById(id: Long): Post {
        return dao.findById(id)
    }
}

