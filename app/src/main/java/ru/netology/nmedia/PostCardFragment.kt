package ru.netology.nmedia


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dto.Post
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import util.AndroidUtils
import viewmodel.PostViewModel

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

        val currentPost = viewModel.edited.value

        fun bind(post: Post) {
            binding.apply {

                avatar.setImageResource(R.drawable.post_avatar)
                author.text = post.author
                published.text = post.published
                content.text = post.content

                like.isChecked = post.likedByMe
                like.text = AndroidUtils.formatNum(post.likes)

                repost.isChecked = post.repostedByMe
                repost.text = AndroidUtils.formatNum(post.reposts)

                comment.isChecked = post.commentedByMe
                comment.text = AndroidUtils.formatNum(post.comments)

                views.text = AndroidUtils.formatNum(post.views)

                videoPreview.isVisible = post.video.isNotBlank()

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

        if (currentPost != null) {
            bind(currentPost)
        }

        return binding.root
    }
}

