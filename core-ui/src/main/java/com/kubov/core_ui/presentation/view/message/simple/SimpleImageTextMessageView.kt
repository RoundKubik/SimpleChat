package com.kubov.core_ui.presentation.view.message.simple

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import com.kubov.core_ui.presentation.view.message.base.ContainerMessageView
import com.kubov.core_ui.presentation.view.message.content.ImageTextContentView

/**
 * Implements logic to display chat message with text and image
 */
class SimpleImageTextMessageView : ContainerMessageView<ImageTextContentView> {

    var onMessageClickListener: ((chatId: Long, messageId: Long) -> Unit)? = null

    private val messageContentView = ImageTextContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setContentView(messageContentView)

        setHasMessagesTitle(false)

        messageContentView.onImageClickListener = {
            if (messageId != null && chatId != null) {
                onMessageClickListener?.invoke(chatId!!, messageId!!)
            }
        }
    }

    override fun showMessageContent(contentView: ImageTextContentView?, message: Message) {
        contentView?.showMessage(message)
    }
}