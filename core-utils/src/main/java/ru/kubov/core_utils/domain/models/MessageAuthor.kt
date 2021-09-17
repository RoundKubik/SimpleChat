package ru.kubov.core_utils.domain.models

// will be used to support channels logic
// TODO: 17.09.2021 will be removed or used in future
data class MessageAuthor(
    var userId: Long?,
    var name: String,
    var photoUrl: String?
) {
    val isUser: Boolean
        get() = userId != null
}