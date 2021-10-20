package ru.kubov.core_utils.domain.models

// TODO: 20.10.2021
data class ChatInfo(
    val id: Long,
    val chatLogo: String,
    val chatTitle: String,
    val chatDescription: String?,
    val chatShortInfo: String,
    val isPrivate: Boolean,
    val role: UserChatRole?,
    val members: Long,
    val mediaCount: Long,
    val isTread: Boolean
) {
    val isAdmin: Boolean
        get() = role in listOf(UserChatRole.Owner, UserChatRole.Admin)
}