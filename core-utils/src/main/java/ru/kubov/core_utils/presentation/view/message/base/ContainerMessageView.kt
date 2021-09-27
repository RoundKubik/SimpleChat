package ru.kubov.core_utils.presentation.view.message.base

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import ru.kubov.core_utils.R
import ru.kubov.core_utils.databinding.ViewMessagesAttachmentBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageStatus
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.*
import java.util.*

/**
 * Base class implements logic of container view
 *
 */
abstract class ContainerMessageView<CV : View> : LinearLayout {

    companion object {
        private const val LOCAL_MESSAGE_ALPHA = 0.4f
        private const val LOCAL_LOADER_ALPHA_DELAY_MS = 3000L
        private const val HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS = 300L
        private const val HIGHLIGHT_WAIT_ANIM_DURATION_MS = 1500L

        const val HIGHLIGHT_ANIM_DURATION_MS = HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS + HIGHLIGHT_WAIT_ANIM_DURATION_MS
    }

    /**
     * Callback when user clicked on quoted message
     *
     *  messageId - id of quoted message
     */
    var onQuotedMessageClickListener: ((messageId: Long) -> Unit)? = null

    protected var chatId: Long? = null
    protected var messageId: Long? = null
    protected var quotedMessageId: Long? = null

    // TODO: 16.09.2021 remove and get from stylable
    private val highlightColor = ContextCompat.getColor(context, R.color.highlight_color)
    private var backgroundAnimator: AnimatorSet? = null

    private var _binding: ViewMessagesAttachmentBinding? = null
    private val binding get() = _binding!!

    private val showLocalAlphaRunnable = Runnable {
        showLocalAlpha(true)
    }

    private var messageContentView: CV? = null
    private var contentLayoutParams: LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    private var hasNewMessagesTitle: Boolean = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        orientation = VERTICAL
        _binding = ViewMessagesAttachmentBinding.inflate(LayoutInflater.from(context), this)
        binding.viewMessagesAttachmentQcmvQuotedMessage.setDebounceClickListener {
            quotedMessageId?.let { id -> onQuotedMessageClickListener?.invoke(id) }
        }
    }

    /**
     * Base fun provides implementation of showing message in content view
     * It should be override with call method of [contentView] showing message info inside [message]
     *
     * @param contentView - view hold by this class [ContainerMessageView]
     * @param message - displayed message
     */
    protected abstract fun showMessageContent(contentView: CV?, message: Message)

    override fun onDetachedFromWindow() {
        removeCallbacks(showLocalAlphaRunnable)
        super.onDetachedFromWindow()
    }

    final override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
    }

    /**
     *  Setting content message view
     *
     *  @param messageContentView - content message di
     */
    fun setContentView(messageContentView: CV) {
        this.messageContentView = messageContentView
        replaceStubToContentView()
    }

    /**
     *  Setting has message title if it new message
     *
     *  @param hasNewMessagesTitle - parameter of new message title
     */
    fun setHasMessagesTitle(hasNewMessagesTitle: Boolean) {
        this.hasNewMessagesTitle = hasNewMessagesTitle
    }


    /**
     *  Setting layout params of [messageContentView]
     *
     *  @param contentLayoutParams
     */
    fun setContentLayoutParams(contentLayoutParams: LayoutParams) {
        this.contentLayoutParams = contentLayoutParams
        requestLayout()
    }

    /**
     * Set default message click listener
     * @param onClickListener - click listener when user clicked on message
     */
    fun setOnMessageClickListener(onClickListener: (View) -> Unit) {
        binding.root.setDebounceClickListener {
            onClickListener.invoke(it)
        }
    }

    /**
     * Set default long click message listener
     * @param onClickListener - listener when user clicked on view long time
     */
    fun setOnMessageLongClickListener(onClickListener: (View) -> Boolean) {
        binding.root.setOnLongClickListener(onClickListener)
    }

    /**
     * Showing header that current [ContainerMessageView] is new message
     * @param show - param to show or not show message header
     */
    fun showNewMessagesHeader(show: Boolean) {
        binding.viewMessagesAttachmentTvHeaderNewMessage.isVisible = show
    }

    /**
     * Setting paddings to all content views
     */
    fun setAttachmentPaddings(
        attachmentMarginLeft: Int = 0,
        attachmentMarginTop: Int = 0,
        attachmentMarginRight: Int = 0,
        attachmentMarginBottom: Int = 0
    ) {
        binding.viewMessagesAttachmentQcmvQuotedMessage.setPadding(
            attachmentMarginLeft,
            attachmentMarginTop,
            attachmentMarginRight,
            attachmentMarginBottom
        )
        binding.viewMessagesAttachmentTvSendingMessageFailed.setPadding(
            attachmentMarginLeft, attachmentMarginTop, attachmentMarginRight, attachmentMarginBottom
        )
        invalidate()
    }


    /**
     * Method to showing [Message] in content view
     */
    fun showMessage(message: Message) {

        cacheMessageData(message)

        /**
         * Calling showing message callback
         */
        showMessageContent(messageContentView, message)

        showQuotedMessage(message)

        showFailedLabel(message)

        //updateLoader(message)
    }

    /**
     *  Start animation of highlighting message
     */
    fun startHighlightAnimation(startPlayTime: Long) {
        if (backgroundAnimator?.isRunning == true) {
            return
        }

        val anim1 = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, highlightColor)
        anim1.duration = HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS
        anim1.addUpdateListener { animator ->
            binding.root.setBackgroundColor(animator.animatedValue as Int)
        }

        val anim2 = ValueAnimator.ofInt(0, 0)
        anim2.duration = HIGHLIGHT_WAIT_ANIM_DURATION_MS

        val anim3 = ValueAnimator.ofObject(ArgbEvaluator(), highlightColor, Color.TRANSPARENT)
        anim3.duration = HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS
        anim3.addUpdateListener { animator ->
            binding.root.setBackgroundColor(animator.animatedValue as Int)
        }
        AnimatorSet().apply {
            playSequentially(anim1, anim2, anim3)
            addListener(
                onStart = { a -> backgroundAnimator = a as AnimatorSet },
                onCancel = { binding.root.setBackgroundColor(Color.TRANSPARENT) },
                onEnd = { backgroundAnimator = null })
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentPlayTime = startPlayTime
            }
            start()
        }
    }

    /**
     * Method for clear background animation
     */
    fun clearBackgroundAnimation() {
        backgroundAnimator?.cancel()
        backgroundAnimator = null
    }

    /**
     * Caching message information if it necessary
     */
    private fun cacheMessageData(message: Message) {
        chatId = message.chatId
        messageId = message.id
        quotedMessageId = message.quotedMessage?.id
    }

    private fun showLocalAlpha(show: Boolean) {
        removeCallbacks(showLocalAlphaRunnable)
        val alphaValue = if (show) {
            LOCAL_MESSAGE_ALPHA
        } else {
            1.0f
        }
        messageContentView?.setAlphaIfNew(alphaValue)
    }

    private fun showLocalAlphaDelayed(delay: Long) {
        showLocalAlpha(false)
        postDelayed(showLocalAlphaRunnable, delay)
    }

    private fun showQuotedMessage(message: Message) {
        if (message.quotedMessage != null) {
            with(binding) {
                viewMessagesAttachmentQcmvQuotedMessage.setQuotedMessage(message.quotedMessage!!)
                viewMessagesAttachmentQcmvQuotedMessage.isVisible = true
            }
        } else {
            binding.viewMessagesAttachmentQcmvQuotedMessage.isVisible = false
        }
    }

    private fun showFailedLabel(message: Message) {
        binding.viewMessagesAttachmentTvSendingMessageFailed.isVisible =
            message.isLocal && message.messageStatus == MessageStatus.Failed
    }

    private fun updateLoader(message: Message) {
        when {
            message.isLocal && message.messageStatus == MessageStatus.Failed -> {
                showLocalAlpha(true)
            }
            message.isLocal && message.messageType == MessageType.SingleImage -> {
                val dateDiffMs = Date().time - message.date.time
                if (dateDiffMs < 0 || dateDiffMs > LOCAL_LOADER_ALPHA_DELAY_MS) {
                    showLocalAlpha(true)
                } else {
                    showLocalAlphaDelayed(LOCAL_LOADER_ALPHA_DELAY_MS - dateDiffMs)
                }
            }
            message.isLocal -> {
                val dateDiffMs = Date().time - message.date.time
                if (dateDiffMs < 0 || dateDiffMs > LOCAL_LOADER_ALPHA_DELAY_MS) {
                    showLocalAlpha(true)
                } else {
                    val delay = LOCAL_LOADER_ALPHA_DELAY_MS - dateDiffMs
                    showLocalAlphaDelayed(delay)
                }
            }
            else -> {
                showLocalAlpha(false)
            }
        }
    }

    private fun replaceStubToContentView() {
        indexOfChild(binding.viewMessagesAttachmentVsViewStub).apply {
            val originalLp = binding.viewMessagesAttachmentVsViewStub.layoutParams as LayoutParams
            contentLayoutParams.leftMargin += originalLp.leftMargin
            contentLayoutParams.topMargin += originalLp.topMargin
            contentLayoutParams.rightMargin += originalLp.rightMargin
            contentLayoutParams.bottomMargin += originalLp.bottomMargin
            removeViewAt(this)
            addView(messageContentView, this, contentLayoutParams)
        }
    }
}