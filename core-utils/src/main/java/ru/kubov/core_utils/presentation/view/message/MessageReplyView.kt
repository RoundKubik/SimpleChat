package ru.kubov.core_utils.presentation.view.message

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import ru.kubov.core_utils.databinding.ViewMessageReplyBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.dpToPx
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage

// TODO: 12.09.2021  
class MessageReplyView : FrameLayout {

    companion object {
        private const val SHOW_ANIM_DURATION_MS = 150L
        private const val HIDE_ANIM_DURATION_MS = 100L
        private const val VIEW_MAX_HEIGHT = 64
        private const val MARGIN_HAS_IMAGE = 120
        private const val MARGIN_HAS_NOT_IMAGE = 64
    }

    // TODO: 12.09.2021
    var onHideAnimationEndListener: (() -> Unit)? = null

    private val maxHeight: Int = dpToPx(VIEW_MAX_HEIGHT)

    private var showHideAnimator: ValueAnimator? = null

    private var _binding: ViewMessageReplyBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        _binding = ViewMessageReplyBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            maxHeight.coerceAtMost(
                MeasureSpec.getSize(
                    heightMeasureSpec
                )
            ), MeasureSpec.EXACTLY
        )
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    // TODO: 12.09.2021
    fun setOnRemoveClickListener(onClickListener: OnClickListener) {
        binding.viewMessageReplyIvClose.setDebounceClickListener {
            onClickListener.onClick(it)
        }
    }

    // TODO: 12.09.2021
    fun showMessage(message: Message) {
        val hasImage = message.messageType in arrayOf(MessageType.SingleImage)

        binding.viewMessageReplyTvAuthor.text = message.userAuthor?.name ?: message.author.name
        val avatarUrl = if (message.userAuthor != null) {
            message.userAuthor?.photoUrl
        } else {
            message.author.photoUrl
        }
        binding.viewMessageReplySdvAvatar.showImage(avatarUrl)
        binding.viewMessageReplyTvMessage.updateLayoutParams<LayoutParams> {
            rightMargin = if (hasImage) {
                dpToPx(MARGIN_HAS_IMAGE)
            } else {
                dpToPx(MARGIN_HAS_NOT_IMAGE)
            }
        }
        binding.viewMessageReplySdvReplyImage.isVisible = hasImage
        binding.viewMessageReplySdvReplyImage.showImage(message.photo?.uri)
        binding.viewMessageReplyTvMessage.text = message.text
    }

    // TODO: 12.09.2021  
    fun animateShow() {
        if (showHideAnimator?.isRunning == true) showHideAnimator?.cancel()

        showHideAnimator = ValueAnimator.ofInt(height, maxHeight)
        showHideAnimator?.duration = SHOW_ANIM_DURATION_MS
        showHideAnimator?.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            updateLayoutParams { height = value }
        }
        showHideAnimator?.start()
    }

    // TODO: 12.09.2021
    fun animateHide() {
        if (showHideAnimator?.isRunning == true) showHideAnimator?.cancel()

        showHideAnimator = ValueAnimator.ofInt(height, 0)
        showHideAnimator?.duration = HIDE_ANIM_DURATION_MS
        showHideAnimator?.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            updateLayoutParams { height = value }
        }
        showHideAnimator?.addListener(onEnd = { onHideAnimationEndListener?.invoke() })
        showHideAnimator?.start()
    }
}