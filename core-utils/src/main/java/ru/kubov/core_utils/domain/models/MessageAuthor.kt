package ru.kubov.core_utils.domain.models

// TODO: 13.09.2021 check logic
// will be used to support channels logic
data class MessageAuthor(
    var userId: Long?,
    var name: String,
    var photoUrl: String?
) {
    val isUser: Boolean
        get() = userId != null
}