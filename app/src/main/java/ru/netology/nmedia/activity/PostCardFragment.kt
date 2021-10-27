package ru.netology.nmedia.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.activity.R
import ru.netology.activity.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


class PostCardFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCardPostBinding.inflate(
            inflater,
            container,
            false
        )

        val post = viewModel.edited.value ?: Post()

        bind(post, binding)

        return binding.root
    }

    private fun bind(post: Post, binding: FragmentCardPostBinding) {

        binding.apply {
            avatar.setImageResource(R.drawable.post_avatar)
            author.text = post.author
            published.text = post.published
            content.text = post.content
            content.movementMethod = ScrollingMovementMethod()

            like.isChecked = post.likedByMe
            like.text = AndroidUtils.formatNum(post.likes)

            repost.isChecked = post.repostedByMe
            repost.text = AndroidUtils.formatNum(post.reposts)

            comment.isChecked = post.commentedByMe
            comment.text = AndroidUtils.formatNum(post.comments)

            views.text = AndroidUtils.formatNum(post.views)

            videoPreview.isVisible = post.video.isNotBlank()

            like.setOnClickListener {
                viewModel.likeById(post.id)
            }

            comment.setOnClickListener {
                viewModel.commentById(post.id)
            }

            repost.setOnClickListener {
                viewModel.repostById(post.id)
            }

            playButton.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video)
                }
                startActivity(intent)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                findNavController().navigate(R.id.action_postCardFragment_to_feedFragment)
                                true
                            }
                            R.id.edit -> {
                                findNavController().navigate(R.id.action_postCardFragment_to_editPostFragment)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }

    }
}

