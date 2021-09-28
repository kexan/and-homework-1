package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->
            with(binding) {
                avatar.setImageResource(R.drawable.post_avatar)
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likeCount.text = formatNum(post.likes)
                repostsCount.text = formatNum(post.reposts)
                commentsCount.text = formatNum(post.comments)
                viewsCount.text = formatNum(post.views)
                if (!post.likedByMe) {
                    like.setImageResource(R.drawable.ic_like)
                } else {
                    like.setImageResource(R.drawable.ic_liked)
                }
            }
        }

        binding.like.setOnClickListener {
            viewModel.like()
        }

        binding.repost.setOnClickListener {
            viewModel.repost()
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