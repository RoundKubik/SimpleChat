package ru.kubov.core_utils.presentation.view.message

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ViewQuotedChatMessageBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.dpToPx
import ru.kubov.core_utils.extensions.setPaddingRight
import ru.kubov.core_utils.extensions.showImage

/**
 * Class implements presentation of quoted message
 */
class QuotedChatMessageView : FrameLayout {

    companion object {
        private const val MEASURE_SPEC_SIZE = 40
        private const val RIGHT_PADDING_HAS_IMAGE = 56
        private const val RIGHT_PADDING_HAS_NOT_IMAGE = 12
    }

    private var _binding: ViewQuotedChatMessageBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        _binding = ViewQuotedChatMessageBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(dpToPx(MEASURE_SPEC_SIZE), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    /**
     *  Method provides show quoted message content
     *
     *  @param quotedMessage - message data
     */
    fun setQuotedMessage(quotedMessage: Message) {

        val hasImage = quotedMessage.messageType in arrayOf(MessageType.SingleImage)
        val paddingRight = dpToPx(
            if (hasImage) {
                RIGHT_PADDING_HAS_IMAGE
            } else {
                RIGHT_PADDING_HAS_NOT_IMAGE
            }
        )
        binding.viewMessageReplyTvAuthor.setPaddingRight(paddingRight)
        binding.viewMessageReplyTvMessage.setPaddingRight(paddingRight)
        binding.viewMessageReplyTvAuthor.text = quotedMessage.user?.name ?: quotedMessage.messageAuthor.name
        binding.viewMessageReplyTvMessage.text = quotedMessage.text
        binding.viewMessageReplySdvReplyImage.isVisible = hasImage
        binding.viewMessageReplySdvReplyImage.showImage(quotedMessage.photo?.uri)
    }

}