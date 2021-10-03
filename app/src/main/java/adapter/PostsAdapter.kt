package adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dto.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnRepostListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onRepostListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            avatar.setImageResource(R.drawable.post_avatar)
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = formatNum(post.likes)
            repostsCount.text = formatNum(post.reposts)
            commentsCount.text = formatNum(post.comments)
            viewsCount.text = formatNum(post.views)

            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like
            )

            like.setOnClickListener {
                onLikeListener(post)
            }

            repost.setOnClickListener {
                onRepostListener(post)
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


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}