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
import ru.netology.nmedia.activity.EditPostFragment.Companion.textArg
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

        val currentPost = viewModel.edited.value ?: Post()

        viewModel.edited.observe(viewLifecycleOwner, {

            binding.apply {
                avatar.setImageResource(R.drawable.post_avatar)
                author.text = it.author
                published.text = it.published
                content.text = it.content
                content.movementMethod = ScrollingMovementMethod()

                like.isChecked = it.likedByMe
                like.text = AndroidUtils.formatNum(it.likes)

                repost.isChecked = it.repostedByMe
                repost.text = AndroidUtils.formatNum(it.reposts)

                comment.isChecked = it.commentedByMe
                comment.text = AndroidUtils.formatNum(it.comments)

                views.text = AndroidUtils.formatNum(it.views)

                videoPreview.isVisible = it.video.isNotBlank()
            }
        })

        with(binding) {

            like.setOnClickListener {
                viewModel.likeById(currentPost.id)
                update(currentPost.id)
            }

            comment.setOnClickListener {
                viewModel.commentById(currentPost.id)
                update(currentPost.id)
            }

            repost.setOnClickListener {
                viewModel.repostById(currentPost.id)

                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, currentPost.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

                update(currentPost.id)
            }

            playButton.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(currentPost.video)
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
                                findNavController().navigate(
                                    R.id.action_postCardFragment_to_editPostFragment,
                                    Bundle().apply {
                                        textArg = currentPost.content
                                    }
                                )
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
        return binding.root
    }

    private fun update(id: Long) {
        viewModel.edited.value = viewModel.findById(id)
    }
}


