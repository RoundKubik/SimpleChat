package ru.kubov.core_utils.domain.models

sealed class UserChatRole {
    object Owner : UserChatRole()
    object Admin : UserChatRole()
    object Subscriber : UserChatRole()
    data class Unsupported(val string: String) : UserChatRole()
}