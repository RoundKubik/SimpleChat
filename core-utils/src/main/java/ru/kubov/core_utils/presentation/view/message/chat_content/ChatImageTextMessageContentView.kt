package ru.kubov.core_utils.presentation.view.message.chat_content

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.updateLayoutParams
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.extensions.dpToPx
import ru.kubov.core_utils.presentation.view.message.base.ContainerLeadMessageContentView
import ru.kubov.core_utils.presentation.view.message.content.ImageTextContentView

/**
 * Class implements presentation of view with avatar and nickname, message text and image
 */
class ChatImageTextMessageContentView : ContainerLeadMessageContentView<ImageTextContentView> {

    companion object {
        private const val BASE_TOP_MARGIN = 22
        private const val ADDITION_TOP_MARGIN_MESSAGE_TEXT_EMPTY = 8
        private const val ADDITION_TOP_MARGIN_MESSAGE_TEXT_NOT_EMPTY = 0
    }

    private var messageContentView = ImageTextContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setMessageContentView(messageContentView)
    }

    override fun showMessageContent(contentView: ImageTextContentView?, message: Message) {
        contentView?.updateLayoutParams<MarginLayoutParams> {
            topMargin =
                dpToPx(
                    BASE_TOP_MARGIN + if (message.text.isNotEmpty()) {
                        ADDITION_TOP_MARGIN_MESSAGE_TEXT_NOT_EMPTY
                    } else {
                        ADDITION_TOP_MARGIN_MESSAGE_TEXT_EMPTY
                    }
                )
        }
        contentView?.showMessage(message)
    }

    /**
     * Setter listener of clicking on image
     */
    fun setOnImageClickListener(listener: (() -> Unit)?) {
        messageContentView.onImageClickListener = listener
    }
}