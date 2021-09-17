package ru.kubov.core_utils.domain.models

import java.util.*

/**
 * Class implements data for show message
 *
 * @param id - standard id of message
 * @param chatId - id of chat where sent current message
 * @param text - text of message
 * @param date - date of sent message
 * @param isLocal - flag keeps value true if it message not sent and false if message just sent
 * @param messageType - type of message [MessageType] can be text or image
 * @param messageAuthor - keeps info about message author, may be used in implementation channels
 * @param messageStatus - status of message keeps information is message uploaded, loaded, ready or failed
 * @param photo - default photo, showed in app
 * @param forwardedMessageId - value with information about forwarded message
 * @param user - current logged app user
 * @param quotedMessage - message keeps info about quoted message
 * @param forwardedMessage - keeps forwarded info about message
 */
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
    val forwardedMessage: Message?
)