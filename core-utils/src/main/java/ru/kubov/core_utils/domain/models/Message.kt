package ru.kubov.core_utils.domain.models

import java.util.*

// TODO: 13.09.2021 add documentation 
data class Message(
    var id: Long,
    var chatId: Long,
    var text: String,
    var date: Date,
    var isLocal: Boolean,
    val messageType: MessageType,
    var messageAuthor: MessageAuthor,
    var messageStatus: MessageStatus?,
    var photo: Photo?,
    var forwardedMessageId: ForwardedMessageId?,
    var user: User?,
    var quotedMessage: Message?,
    var link: MessageLink?,
    val forwardedMessage: Message?
)