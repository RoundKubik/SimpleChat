package ru.kubov.core_utils.domain.models

data class MessageLink(
    var url: String,
    var title: String,
    var desc: String,
    var photoUrl: String?,
    var chatId: Long?
)