package com.kuboc.core_ui.presentation.view.message.chat

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import com.kuboc.core_ui.presentation.view.message.base.ContainerMessageView
import com.kuboc.core_ui.presentation.view.message.chat_content.ChatImageTextMessageContentView

/**
 * Implements logic to display chat message with text and image
 */
class ChatImageTextMessageView : ContainerMessageView<ChatImageTextMessageContentView> {

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
        contentView?.showMessage(message)
    }
}