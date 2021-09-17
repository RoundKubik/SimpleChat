package ru.kubov.core_utils.extensions

import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest

/**
 * Extension method for Fresco [SimpleDraweeView] to create image request from [Uri]
 * @param uri - image uri
 */
fun SimpleDraweeView.showImage(uri: Uri?) {
    setImageRequest(ImageRequest.fromUri(uri))
}

/**
 * Extension method for Fresco [SimpleDraweeView] to create image request from [String]
 * @param uri - image uri
 */
fun SimpleDraweeView.showImage(uri: String?) {
    setImageRequest(ImageRequest.fromUri(uri))
}

