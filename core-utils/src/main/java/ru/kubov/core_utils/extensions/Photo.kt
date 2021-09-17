package ru.kubov.core_utils.extensions

import android.net.Uri
import ru.kubov.core_utils.domain.models.Photo

/**
 * Extension method provide parse string [Photo.uri] to [Uri]
 */
fun Photo.imageUri(): Uri = Uri.parse(uri)
