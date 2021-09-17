package ru.kubov.core_utils.domain.models

/**
 * Id of forwarded message
 *
 * @param messageId - id of current forwarded message
 * @param chatId - id of chat forwarded message
 * @param chatName - name of chat keeps forwarded message
 */
data class ForwardedMessageId(
    var messageId: Long,
    var chatId: Long,
    var chatName: String
)