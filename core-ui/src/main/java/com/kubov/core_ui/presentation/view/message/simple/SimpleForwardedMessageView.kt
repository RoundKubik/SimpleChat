package com.kubov.core_ui.presentation.view.message.simple

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import com.kubov.core_ui.presentation.view.message.base.ContainerMessageView
import com.kubov.core_ui.presentation.view.message.content.ForwardedContentView

/**
 * Implements logic to display chat forwarded message
 */
class SimpleForwardedMessageView : ContainerMessageView<ForwardedContentView> {

    private val messageContentView = ForwardedContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setContentView(messageContentView)
        setHasMessagesTitle(false)
    }

    override fun showMessageContent(contentView: ForwardedContentView?, message: Message) {
        messageContentView.showForwardedMessage(message)
    }

    /**
     * Setter click listener for forwarded message
     * @param onForwardedMessageListener - listener get parameters of id forwarded chat and id of message
     */
    fun setForwardedMessageClickListener(onForwardedMessageListener: ((forwardedChatId: Long, messageId: Long) -> Unit)?) {
        messageContentView.onForwardedMessageClickListener = onForwardedMessageListener
    }

    /**
     * Setter click listener for image in forwarded message
     * @param onImageClickListener - listener get parameters of id forwarded chat and id of message
     */
    fun setOnImageClickListener(onImageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)?) {
        messageContentView.onImageClickListener = onImageClickListener
    }


}