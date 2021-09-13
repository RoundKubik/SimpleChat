package ru.kubov.core_utils.domain.models

import android.net.Uri

/**
 * Model of photo in apps
 */
data class Photo(
    var id: Long,
    var uri: String,
    var isLocal: Boolean
) {

    /**
     * Parse image uri 
     */
    // TODO: 13.09.2021 remove from this model 
    fun imageUri(): Uri = Uri.parse(uri)
}
