package com.kuboc.core_ui.presentation.view.message.content

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.kuboc.core_ui.databinding.ViewForwardedContentViewBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.setDebounceClickListener

/**
 * Class implements presentation of forwarded message
 */
class ForwardedContentView : RelativeLayout {

    /**
     * Listener if user clicked on image in forwarded message
     */
    var onImageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null
        set(value) {
            field = value
            val listener = OnClickListener {
                if (forwardedChatId != null && forwardedMessageId != null) {
                    field?.invoke(forwardedChatId!!, forwardedMessageId!!)
                }
            }
            binding.viewForwardedContentViewIcvImageContent.setDebounceClickListener(onClickListener = listener)
        }

    /**
     * Listener if user clicked on user avatar or user nickname
     */
    var onForwardedMessageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null
        set(value) {
            field = value
            val listener = OnClickListener {
                if (forwardedChatId != null && forwardedMessageId != null) {
                    field?.invoke(forwardedChatId!!, forwardedMessageId!!)
                }
            }
            binding.viewForwardedContentViewSdvAvatar.setDebounceClickListener(onClickListener = listener)
            binding.viewForwardedContentViewSdvNickname.setDebounceClickListener(onClickListener = listener)
        }

    private var _binding: ViewForwardedContentViewBinding? = null
    private val binding get() = _binding!!

    private var forwardedChatId: Long? = null
    private var forwardedMessageId: Long? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewForwardedContentViewBinding.inflate(LayoutInflater.from(context), this)
    }

    /**
     * Provide presentation of forwarded message
     */
    fun showForwardedMessage(message: Message) {
        forwardedChatId = message.chatId
        forwardedMessageId = message.id


        if (message.user != null) {
            binding.viewForwardedContentViewSdvNickname.text = message.user?.name
        } else {
            binding.viewForwardedContentViewSdvNickname.text = message.messageAuthor.name
        }

        when(message.messageType) {
            MessageType.Text -> {
                binding.viewForwardedContentViewIcvImageContent.isVisible = false
                binding.viewForwardedContentViewTcvTextContent.isVisible = true
                binding.viewForwardedContentViewTcvTextContent.showMessage(message)
            }
            MessageType.SingleImage -> {
                binding.viewForwardedContentViewTcvTextContent.isVisible = false
                binding.viewForwardedContentViewIcvImageContent.isVisible = true
                binding.viewForwardedContentViewIcvImageContent.showMessage(message)
            }
        }
    }
}