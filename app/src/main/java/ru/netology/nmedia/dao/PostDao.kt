//package ru.netology.nmedia.dao
//
//import ru.netology.nmedia.dto.Post
//
//interface PostDao {
//    fun getAll(): List<Post>
//    fun save(post: Post): Post
//    fun likeById(id: Long)
//    fun repostById(id: Long)
//    fun commentById(id: Long)
//    fun removeById(id: Long)
//
//}

package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query(
        """
        UPDATE PostEntity SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun likeById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               reposts = reposts + 1,
               repostedByMe = 1
           WHERE id = :id
        """
    )
    fun repostById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               comments = comments + CASE WHEN commentedByMe THEN -1 ELSE 1 END,
               commentedByMe = CASE WHEN commentedByMe THEN 0 ELSE 1 END
           WHERE id = :id;
        """
    )
    fun commentById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

    @Query("SELECT * FROM PostEntity WHERE id = :id")
    fun findById(id: Long): Post
}