package com.kubov.core_ui.presentation.chats.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kubov.core_ui.presentation.view.chat.ChatView
import ru.kubov.core_utils.domain.models.Chat

// TODO: 02.10.2021 add documentation
open class ChatsAdapter(private val onChatClick: ((Chat?) -> Unit)? = null) :
    ListAdapter<Chat, ChatsAdapter.ChatViewHolder>(
        ChatDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatView(parent.context)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindChat(getItem(position))
    }

    inner class ChatViewHolder(
        val view: ChatView
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.onChatClickListener = {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onChatClick?.invoke(getItem(position))
                }
            }
        }

        fun bindChat(chat: Chat) {
            view.showChat(chat)
        }
    }
}