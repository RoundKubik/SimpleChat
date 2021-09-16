package ru.kubov.core_utils.presentation.view.message.chat

import android.annotation.SuppressLint
import android.content.Context
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.utils.DateFormatter
import ru.kubov.core_utils.presentation.view.message.base.ContainerMessageView

/**
 * Class implements chat message with author icon author title and text message
 */
@SuppressLint("ViewConstructor")
class ChatTextMessageView(
    context: Context,
    attachmentMarginLeft: Int,
    attachmentMarginRight: Int,
    dateFormatter: DateFormatter,
    listener: Listener,
) : ContainerMessageView<ChatTextMessageContentView>(
    context = context,
    messageContentView = ChatTextMessageContentView(context, dateFormatter).apply {
        setOnAuthorClickListener {
            listener.onMessageAuthorClick(it)
        }
    },
    attachmentMarginLeft = attachmentMarginLeft,
    attachmentMarginRight = attachmentMarginRight,
    hasNewMessagesTitle = true,
    listener = listener
) {

    override fun showMessageContent(contentView: ChatTextMessageContentView, message: Message) {
        contentView.showMessage(message)
    }
}