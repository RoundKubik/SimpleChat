package com.kuboc.core_ui.presentation.view.message.simple

import android.content.Context
import android.util.AttributeSet
import ru.kubov.core_utils.domain.models.Message
import com.kuboc.core_ui.presentation.view.message.base.ContainerMessageView
import com.kuboc.core_ui.presentation.view.message.content.TextContentView

/**
 * Class implements chat message with author icon author title and text message
 */
class SimpleTextMessageView : ContainerMessageView<TextContentView> {

    private val contentView = TextContentView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        setContentView(contentView)
        setHasMessagesTitle(false)
    }

    override fun showMessageContent(contentView: TextContentView?, message: Message) {
        contentView?.showMessage(message)
    }
}