package ru.kubov.core_utils.presentation.view.message.chat

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.presentation.view.message.base.ContainerMessageView

/**
 * Implements logic to display chat forwarded message
 */
class ChatForwardedView : ContainerMessageView<ChatForwardedContentView> {

    var onMessageClickListener: ((chatId: Long, messageId: Long) -> Unit)? = null

    private val messageContentView = ChatImageTextMessageContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setContentView(messageContentView)

        setHasMessagesTitle(false)

        messageContentView.setOnImageClickListener {
            if (messageId != null && chatId != null) {
                onMessageClickListener?.invoke(chatId!!, messageId!!)
            }
        }
    }

    override fun showMessageContent(contentView: ChatImageTextMessageContentView?, message: Message) {

    }

    override fun showMessageContent(contentView: ChatForwardedContentView?, message: Message) {
        contentView?.showMessage(message)
    }
}