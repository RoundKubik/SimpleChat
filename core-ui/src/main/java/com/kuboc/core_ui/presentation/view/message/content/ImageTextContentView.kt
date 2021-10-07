package com.kuboc.core_ui.presentation.view.message.content

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.kuboc.core_ui.databinding.ViewImageTextMessageContentBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.setDebounceClickListener

/**
 *  Image and text content view provides displaying text message and image
 */
class ImageTextContentView : LinearLayout {

    /**
     * Set click listener when clicked on image
     *
     */
    var onImageClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.viewImageTextMessageContentTcvImageContent.setDebounceClickListener {
                field?.invoke()
            }
        }

    private var _binding: ViewImageTextMessageContentBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewImageTextMessageContentBinding.inflate(LayoutInflater.from(context), this)
    }

    /**
     * Method provides show message date
     *
     * @param message - displaying message
     */
    fun showMessage(message: Message) {
        message.text.isNotBlank().also {
            binding.viewImageTextMessageContentTmcvTextContent.isVisible = it
            if (it) {
                binding.viewImageTextMessageContentTmcvTextContent.showMessage(message)
            }
        }
        when (message.messageType) {
            MessageType.SingleImage -> binding.viewImageTextMessageContentTcvImageContent.showPhoto(message.photo)
        }
    }


}