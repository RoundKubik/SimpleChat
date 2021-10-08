package com.kubov.core_ui.presentation.view.message.chat

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import com.kubov.core_ui.presentation.view.message.base.ContainerMessageView
import com.kubov.core_ui.presentation.view.message.chat_content.ChatForwardedContentView

/**
 * Implements logic to display chat forwarded message
 */
class ChatForwardedView : ContainerMessageView<ChatForwardedContentView> {

    private val messageContentView = ChatForwardedContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setContentView(messageContentView)
        setHasMessagesTitle(true)
    }

    override fun showMessageContent(contentView: ChatForwardedContentView?, message: Message) {
        contentView?.showMessage(message)
    }

    /**
     * Setter click listener for forwarded message
     * @param onForwardedMessageListener - listener get parameters of id forwarded chat and id of message
     */
    fun setForwardedMessageClickListener(onForwardedMessageListener: ((forwardedChatId: Long, messageId: Long) -> Unit)?) {
        messageContentView.setForwardedMessageClickListener(onForwardedMessageListener)
    }

    /**
     * Setter click listener for image in forwarded message
     * @param onImageClickListener - listener get parameters of id forwarded chat and id of message
     */
    fun setOnImageClickListener(onImageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)?) {
        messageContentView.setOnImageClickListener(onImageClickListener)
    }
}