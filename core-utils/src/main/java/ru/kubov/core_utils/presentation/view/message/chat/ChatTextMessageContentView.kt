package ru.kubov.core_utils.presentation.view.message.chat

import android.annotation.SuppressLint
import android.content.Context
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.utils.DateFormatter
import ru.kubov.core_utils.presentation.view.message.base.ContainerLeadMessageContentView
import ru.kubov.core_utils.presentation.view.message.base.TextContentView

/**
 *  View implements message view in chat with avatar and username
 *  @param context - context to create view
 *  @param dateFormatter - formatter to create properties in [ContainerLeadMessageContentView]
 */
@SuppressLint("ViewConstructor")
class ChatTextMessageContentView(context: Context, dateFormatter: DateFormatter) : ContainerLeadMessageContentView<TextContentView>(
    context = context,
    contentView =  TextContentView(context),
    dateFormatter = dateFormatter
) {
    override fun showMessageContent(contentView: TextContentView, message: Message) {
        contentView.showMessage(message)
    }
}