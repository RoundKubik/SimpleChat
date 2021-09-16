package ru.kubov.core_utils.presentation.view.message.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.use
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.RetainingDataSourceSupplier
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import ru.kubov.core_utils.R
import ru.kubov.core_utils.databinding.ViewImageContentBinding
import ru.kubov.core_utils.domain.models.Photo

/**
 * View implements displaying image in messages
 */
class ImageContentView : FrameLayout {

    private var _binding: ViewImageContentBinding? = null
    private val binding get() = _binding!!

    private val retainingSupplier by lazy { RetainingDataSourceSupplier<CloseableReference<CloseableImage>>() }

    @DrawableRes
    private var progressBarDrawable: Int = 0

    @ColorRes
    private var imagePlaceHolderColor: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewImageContentBinding.inflate(LayoutInflater.from(context), this)
        obtainStyledAttrs(attributeSet, defStyle)
        binding.viewImageContentSdvImage.hierarchy.apply {
            fadeDuration = 0
            actualImageScaleType = ScalingUtils.ScaleType.FOCUS_CROP
            setPlaceholderImage(imagePlaceHolderColor)
        }
    }

    /**
     *  Display image in view provide cached logic by Fresco library
     *
     *  @param photo - displayed image
     */
    fun showPhoto(photo: Photo?) {
        if (photo == null) {
            binding.viewImageContentSdvImage.setImageURI(null as String?)
            return
        }
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(photo.imageUri()).build()
        val cached = Fresco.getImagePipeline().isInDiskCacheSync(imageRequest)

        updateProgressBar(cached)
        showPhoto(photo.isLocal, imageRequest)
    }

    private fun updateProgressBar(cached: Boolean) {
        binding.viewImageContentSdvImage.hierarchy.apply {
            if (cached) {
                setProgressBarImage(null)
            } else {
                setProgressBarImage(progressBarDrawable)
            }
        }
    }

    private fun showPhoto(isLocalPhoto: Boolean, imageRequest: ImageRequest) {
        if (isLocalPhoto) {
            binding.viewImageContentSdvImage.controller = Fresco.newDraweeControllerBuilder()
                .setDataSourceSupplier(retainingSupplier)
                .setImageRequest(null)
                .setOldController(binding.viewImageContentSdvImage.controller)
                .build()
            val newSupplier = Fresco.getImagePipeline()
                .getDataSourceSupplier(imageRequest, null, ImageRequest.RequestLevel.FULL_FETCH)
            retainingSupplier.replaceSupplier(newSupplier)
        } else {
            binding.viewImageContentSdvImage.controller = Fresco.newDraweeControllerBuilder()
                .setDataSourceSupplier(null)
                .setImageRequest(imageRequest)
                .setOldController(binding.viewImageContentSdvImage.controller)
                .build()
        }
    }

    private fun obtainStyledAttrs(attrs: AttributeSet?, defStyleAttrs: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ImageContentView,
            defStyleAttrs,
            0
        ).use {
            progressBarDrawable = it.getResourceId(R.styleable.ImageContentView_progressBarDrawable, 0)
            imagePlaceHolderColor = it.getResourceId(R.styleable.ImageContentView_placeholderImageColor, 0)
        }

    }
}