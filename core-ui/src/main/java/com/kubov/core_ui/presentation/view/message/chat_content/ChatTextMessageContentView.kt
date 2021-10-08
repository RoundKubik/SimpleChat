package com.kubov.core_ui.presentation.view.message.chat_content

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import com.kubov.core_ui.presentation.view.message.base.ContainerLeadMessageContentView
import com.kubov.core_ui.presentation.view.message.content.TextContentView

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