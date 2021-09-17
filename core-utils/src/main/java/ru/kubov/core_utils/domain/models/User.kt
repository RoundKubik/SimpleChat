package ru.kubov.core_utils.domain.models

/**
 * Base model of user
 *
 * @param id - base entity id
 * @param name - nickname of user
 * @param online - info is user online
 * @param photoUrl - url of user photo
 */
data class User(
    var id: Long,
    var name: String,
    var online: Boolean,
    var photoUrl: String?,
)