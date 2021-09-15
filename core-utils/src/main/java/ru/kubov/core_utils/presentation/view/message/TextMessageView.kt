package ru.kubov.core_utils.presentation.view.message

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import ru.kubov.core_utils.domain.models.Message

// TODO: 14.09.2021 add documentation
@SuppressLint("ViewConstructor")
class TextMessageView(
    context: Context,
    attachmentMarginLeft: Int,
    attachmentMarginRight: Int,
    listener: Listener
) : ContainerMessageView<TextMessageContentView>(
    context = context,
    messageContentView = TextMessageContentView(context),
    contentLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
        this.leftMargin = attachmentMarginLeft
        this.rightMargin = attachmentMarginRight
    },
    hasNewMessagesTitle = false,
    attachmentMarginLeft = attachmentMarginLeft,
    attachmentMarginRight = attachmentMarginRight,
    listener = listener
) {

    override fun showMessageContent(contentView: TextMessageContentView, message: Message) {
        contentView.showMessage(message)
    }
}