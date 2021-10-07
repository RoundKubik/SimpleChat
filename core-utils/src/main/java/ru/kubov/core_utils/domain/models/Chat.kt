package ru.kubov.core_utils.domain.models

data class Chat(
    val id: Long,
    val imageLogo: String,
    val chatTitle: String,
    val chatDescription: String?,
    val chatShortInfo: String
)