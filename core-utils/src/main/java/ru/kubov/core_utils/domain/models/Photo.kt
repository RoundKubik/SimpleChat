package ru.kubov.core_utils.domain.models


/**
 * Model of photo in apps
 * @param id - base entity id
 * @param uri - uri or url of photo
 * @param isLocal - value keeps cached this photo or not
 */
data class Photo(
    var id: Long,
    var uri: String,
    var isLocal: Boolean
)
