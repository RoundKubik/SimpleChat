package ru.kubov.core_utils.presentation.view.message.chat_content

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.extensions.dpToPx
import ru.kubov.core_utils.presentation.view.message.base.ContainerLeadMessageContentView
import ru.kubov.core_utils.presentation.view.message.content.ForwardedContentView

/**
 * Class implements presentation of forwarded message in chats
 */
class ChatForwardedContentView : ContainerLeadMessageContentView<ForwardedContentView> {

    companion object {
        private const val TOP_MARGIN = 8
    }

    private val contentView = ForwardedContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setMessageContentView(contentView)
        setContentViewLayoutParams(LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = dpToPx(TOP_MARGIN)
        })
    }

    override fun showMessageContent(contentView: ForwardedContentView?, message: Message) {
        contentView?.showForwardedMessage(message)
    }

    /**
     * Setter click listener for forwarded message
     * @param onForwardedMessageListener - listener get parameters of id forwarded chat and id of message
     */
    fun setForwardedMessageClickListener(onForwardedMessageListener: (forwardedChatId: Long, messageId: Long) -> Unit) {
        contentView.onForwardedMessageClickListener = onForwardedMessageListener
    }

    /**
     * Setter click listener for image in forwarded message
     * @param onImageClickListener - listener get parameters of id forwarded chat and id of message
     */
    fun setOnImageClickListener(onImageClickListener: (forwardedChatId: Long, messageId: Long) -> Unit) {
        contentView.onImageClickListener = onImageClickListener
    }
}