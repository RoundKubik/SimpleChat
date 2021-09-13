package ru.kubov.core_utils.domain.models

data class ForwardedMessageId(
        var messageId: Long,
        var chatId: Long,
        var chatName: String)