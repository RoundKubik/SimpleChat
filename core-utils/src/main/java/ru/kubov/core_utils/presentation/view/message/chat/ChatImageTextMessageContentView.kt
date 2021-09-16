package ru.kubov.core_utils.presentation.view.message.chat

import android.annotation.SuppressLint
import android.content.Context
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.utils.DateFormatter
import ru.kubov.core_utils.presentation.view.message.base.ContainerLeadMessageContentView
import ru.kubov.core_utils.presentation.view.message.base.TextContentView

@SuppressLint("ViewConstructor")
class ChatImageTextMessageContentView(
    context: Context,
    dateFormatter: DateFormatter
) : ContainerLeadMessageContentView<TextContentView>(
    context = context,
    contentView =  TextContentView(context),
    dateFormatter = dateFormatter
) {
    override fun showMessageContent(contentView: TextContentView, message: Message) {
        contentView.showMessage(message)
    }
}