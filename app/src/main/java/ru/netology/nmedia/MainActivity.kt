package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            author = "Сергей",
            content = "Тестовый пост 123",
            published = "26 сентября в 13:57",
            likes = 980,
            comments = 10L,
            reposts = 56L,
            views = 20500L,
            likedByMe = true
        )

        with(binding) {

            avatar.setImageResource(R.drawable.post_avatar)
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likeCount.text = formatNum(post.likes)
            repostsCount.text = formatNum(post.reposts)
            commentsCount.text = formatNum(post.comments)
            viewsCount.text = formatNum(post.views)
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_liked)
            }

            like.setOnClickListener {
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like)
                likeCount.text = if (!post.likedByMe) formatNum(post.likes++) else formatNum(post.likes--)
                post.likedByMe = !post.likedByMe
            }

            repost.setOnClickListener {
                post.reposts++
                repostsCount.text = formatNum(post.reposts)
            }
        }
    }

    private fun formatNum(number: Long): String {
        val doubleNum = number.toDouble()
        when {
            (number >= 1_000_000) -> return String.format("%.1f", doubleNum / 1_000_000) + "M"
            (number >= 10_000) -> return String.format("%.0f", doubleNum / 1_000) + "K"
            (number >= 1_000) -> return String.format("%.1f", doubleNum / 1_000) + "K"
        }
        return "$number"
    }
}