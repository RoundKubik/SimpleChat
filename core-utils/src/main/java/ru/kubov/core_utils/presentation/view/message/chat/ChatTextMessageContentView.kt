package ru.kubov.core_utils.presentation.view.message.chat

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.utils.DateFormatter
import ru.kubov.core_utils.presentation.view.message.base.ContainerLeadMessageContentView
import ru.kubov.core_utils.presentation.view.message.base.TextContentView

/**
 *  View implements message view in chat with avatar and username
 */
class ChatTextMessageContentView : ContainerLeadMessageContentView<TextContentView> {

    private val contentView = TextContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setMessageContentView(contentView)
    }

    override fun showMessageContent(contentView: TextContentView?, message: Message) {
        contentView?.showMessage(message)
    }
}