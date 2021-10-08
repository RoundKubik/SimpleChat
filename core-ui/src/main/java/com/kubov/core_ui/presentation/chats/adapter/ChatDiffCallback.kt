package com.kubov.core_ui.presentation.chats.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.kubov.core_utils.domain.models.Chat

// TODO: 02.10.2021 add documentation 
class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {

    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean = oldItem == newItem
    
}