package com.kubov.core_ui.presentation.chats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kubov.core_ui.databinding.ItemChatInfoBinding
import ru.kubov.core_utils.domain.models.Chat
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage

// TODO: 02.10.2021 add documentation
open class ChatsAdapter(private val onChatClick: ((Chat?) -> Unit)? = null) : ListAdapter<Chat, ChatsAdapter.ChatViewHolder>(
    ChatDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindChat(getItem(position))
    }

    inner class ChatViewHolder(
        val binding: ItemChatInfoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setDebounceClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onChatClick?.invoke(getItem(position))
                }
            }
        }

        fun bindChat(chat: Chat) {

        }
    }
}