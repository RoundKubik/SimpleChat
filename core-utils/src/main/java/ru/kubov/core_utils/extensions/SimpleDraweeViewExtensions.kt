package ru.kubov.core_utils.extensions

import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest

// TODO: 12.09.2021
fun SimpleDraweeView.showImage(uri: Uri?) {
    setImageRequest(ImageRequest.fromUri(uri))
}

// TODO: 12.09.2021
fun SimpleDraweeView.showImage(uri: String?) {
    setImageRequest(ImageRequest.fromUri(uri))
}

