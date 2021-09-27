package ru.kubov.feature_main_impl.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kubov.core_utils.domain.models.*
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.presentation.view.message.chat.ChatImageTextMessageView
import ru.kubov.core_utils.presentation.view.message.chat.ChatTextMessageView
import ru.kubov.core_utils.presentation.view.message.chat_content.ChatTextMessageContentView
import ru.kubov.core_utils.presentation.view.message.content.TextContentView
import ru.kubov.core_utils.presentation.view.message.simple.SimpleTextMessageView
import ru.kubov.feature_main_impl.databinding.FragmentChatBinding
import ru.kubov.feature_main_impl.databinding.IncludeBaseToolbarBinding
import ru.kubov.feature_main_impl.databinding.IncludeChatToolbarBinding
import ru.kubov.feature_main_impl.presentation.chat.adapter.MessagesAdapter
import java.util.*

class ChatFragment : Fragment() {

    companion object {
        // TODO: 10.09.2021 add methods to make bundle for new instance
    }

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var _includeChatToolbarBinding: IncludeChatToolbarBinding? = null
    private val includeChatToolbarBinding get() = _includeChatToolbarBinding!!

    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        _includeChatToolbarBinding = IncludeChatToolbarBinding.bind(binding.frgChatLayoutToolbar.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initMessagesAdapter()
    }

    private fun showUserProfileDialog(userId: Int) {

    }

    private fun initMessagesAdapter() {
        messagesAdapter = MessagesAdapter(requireContext())
        val view = ChatTextMessageView(requireContext())
        binding.frgChatMessagesTest.addView(view)
        view.showMessage( Message(
            124,
            23,
            "First Message",
            Date(),
            isLocal = true,
            MessageType.Text,
            MessageAuthor(
                null,
                "Oleg",
                null
            ),
            null,
            null,
            null,
            User(
                123,
                "roundkubik",
                true,
                null
            ),
            null,
            null
        ))
      /*  binding.frgChatRvMessages.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
        messagesAdapter.messages = mutableListOf(
            Message(
                124,
                23,
                "First Message",
                Date(),
                isLocal = true,
                MessageType.Text,
                MessageAuthor(
                    null,
                    "Oleg",
                    null
                ),
                null,
                null,
                null,
                User(
                    123,
                    "roundkubik",
                    true,
                    null
                ),
                null,
                null
            )
        )
        binding.frgChatRvMessages.adapter = messagesAdapter*/
    }

    private fun initToolbar() {
        includeChatToolbarBinding.includeChatToolbarIvIconBack.setDebounceClickListener {
            // viewModel.back()
        }
    }

    private fun updateChatName(name: String) {
        includeChatToolbarBinding.includeChatToolbarTvTitle.text = name
    }

    private fun updateCountChatMembers(members: Int) {
        includeChatToolbarBinding.includeChatToolbarTvMembersCount.text = members.toString()
    }

    fun updateChatLogo(uri: String) {
        includeChatToolbarBinding.includeChatToolbarSdvChatLogo.setImageURI(uri)
    }

    // TODO: 10.09.2021 open image picker fragment or open picker
    fun openGetImageFragment() {

    }

    // TODO: 10.09.2021 open image from message to full screen 50/50 
    fun openMessageImage() {

    }
}