package com.kubov.core_ui.presentation.view.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.kubov.core_ui.databinding.ViewChatBinding
import ru.kubov.core_utils.domain.models.Chat
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage

// TODO: 08.10.2021  add documentation
class ChatView : RelativeLayout {

    // TODO: 08.10.2021 add dcoumentation
    var onChatClickListener: (() -> Unit)? = null
        set(value) {
            if (field == value) {
                return
            }
            field = value
            binding.root.setDebounceClickListener {
                field?.invoke()
            }
        }

    private var _binding: ViewChatBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewChatBinding.inflate(LayoutInflater.from(context))
    }

    // TODO: 08.10.2021 add documentation
    fun showChat(chat: Chat) {
        with(binding) {
            itemChatInfoSdvChatLogo.showImage(chat.imageLogo)
            itemChatInfoTvChatShortInfo.text = chat.chatShortInfo
            itemChatInfoTvChatDescription.text = chat.chatDescription
            itemChatInfoTvChatTitle.text = chat.chatTitle
        }
    }


}