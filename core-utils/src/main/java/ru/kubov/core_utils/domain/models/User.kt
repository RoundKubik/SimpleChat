package ru.kubov.core_utils.domain.models

data class User(
    var id: Long,
    var name: String,
    var online: Boolean,
    var photoUrl: String?,
)