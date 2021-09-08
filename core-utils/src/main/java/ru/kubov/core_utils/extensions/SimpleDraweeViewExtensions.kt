package ru.kubov.core_utils.extensions

import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest

fun SimpleDraweeView.showImage(uri: Uri?) {
    setImageRequest(ImageRequest.fromUri(uri))
}