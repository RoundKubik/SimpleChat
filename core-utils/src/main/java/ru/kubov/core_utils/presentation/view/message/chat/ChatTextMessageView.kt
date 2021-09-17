package ru.kubov.core_utils.presentation.view.message.chat

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.presentation.view.message.base.ContainerMessageView

/**
 * Class implements chat message with author icon author title and text message
 */
class ChatTextMessageView : ContainerMessageView<ChatTextMessageContentView> {

    /**
     * Click listener on message author avatar
     */
    val onMessageAuthorClickListener: ((userId: Long) -> Unit)? = null

    private val contentView = ChatTextMessageContentView(context).apply {
        setOnAuthorClickListener {
            onMessageAuthorClickListener?.invoke(it)
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setContentView(contentView)
        setHasMessagesTitle(true)
    }

    override fun showMessageContent(contentView: ChatTextMessageContentView?, message: Message) {
        contentView?.showMessage(message)
    }
}