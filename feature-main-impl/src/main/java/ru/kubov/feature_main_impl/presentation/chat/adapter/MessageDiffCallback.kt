package ru.kubov.feature_main_impl.presentation.chat.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.kubov.core_utils.domain.models.Message

class MessageDiffCallback(
    private val oldItems: List<Message>,
    private val newItems: List<Message>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]

}