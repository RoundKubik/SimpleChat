package com.kubov.core_ui.presentation.view.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.kubov.core_ui.databinding.ViewSearchChatBinding
import ru.kubov.core_utils.domain.models.Chat

// TODO: 08.10.2021 add documentation
class SearchChatView : RelativeLayout {

    private var _binding: ViewSearchChatBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewSearchChatBinding.inflate(LayoutInflater.from(context))
    }

    // TODO: 08.10.2021 add documentation
    fun showChatContent(chat: Chat, header: String?) {
        if (header != null) {
            binding.viewSearchChatRvHeader.text = header
        } else {
            binding.viewSearchChatRvHeader.isVisible = false
        }
        binding.viewSearchChatRvChat.showChat(chat)
    }
}