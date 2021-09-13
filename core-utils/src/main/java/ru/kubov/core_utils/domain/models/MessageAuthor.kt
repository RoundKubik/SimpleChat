package ru.kubov.core_utils.domain.models

// TODO: 13.09.2021 check logic 
data class MessageAuthor(
    var userId: Long?,
    var name: String,
    var photoUrl: String?
) {
    val isUser: Boolean
        get() = userId != null
}