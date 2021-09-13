package ru.kubov.core_utils.presentation.view.message

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
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
import kotlin.math.roundToInt

// TODO: 12.09.2021 made more flexible to parse properties from xml file
// change constructor to make more flexible
@SuppressLint("ViewConstructor")
abstract class AbstractMessageView<CV : View>(
    context: Context,
    protected val messageContentView: CV,
    contentLayoutParams: LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT),
    attachmentMarginLeft: Int,
    attachmentMarginRight: Int,
    private val firstInGroupMarginTop: Int = 0,
    private val lastInGroupMarginBottom: Int = 0,
    hasNewMessagesTitle: Boolean = true,
    private val listener: Listener?
) : LinearLayout(context, null, 0) {

    companion object {
        private const val LOCAL_MESSAGE_ALPHA = 0.4f
        private const val LOCAL_LOADER_ALPHA_DELAY_MS = 3000L
        private const val HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS = 300L
        private const val HIGHLIGHT_WAIT_ANIM_DURATION_MS = 1500L
        const val HIGHLIGHT_ANIM_DURATION_MS = HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS + HIGHLIGHT_WAIT_ANIM_DURATION_MS
        private const val HEADER_TEXT_SIZE = 20f
        private const val TOP_PADDING = 24
        private const val BOTTOM_PADDING = 6
        private const val TOP_BOTTOM_DEFAULT_PADDING = 6
    }

    // TODO: 12.09.2021
    interface Listener {

        // TODO: 12.09.2021
        fun onMessageClick(message: Message)

        // TODO: 12.09.2021
        fun onMessageLongClick(message: Message)

        // TODO: 12.09.2021
        fun onThreadClick(message: Message)

        // TODO: 12.09.2021
        fun onMessageAuthorClick(userId: Long)

        // TODO: 12.09.2021
        fun onMessageLinkClick(url: String, linkChatId: Long?)

        // TODO: 12.09.2021
        fun onQuotedMessageClick(messageId: Long)

        // TODO: 12.09.2021
        fun onForwardedMessageClick(chatId: Long, messageId: Long)

        // TODO: 12.09.2021
        fun onMessageImageClick(chatId: Long, messageId: Long)
    }

    protected abstract fun showMessageContent(contentView: CV, message: Message)

    private val highlightColor = ContextCompat.getColor(context, R.color.highlight_color)
    private val tvNewMessagesHeader: TextView? // messages with no author can't have this header
    private val layoutContainer: FrameLayout // layout with message content and attachments
    private var backgroundAnimator: AnimatorSet? = null

    private var _binding: ViewMessagesAttachmentBinding? = null
    private val binding get() = _binding!!

    private val showLocalAlphaRunnable = Runnable {
        showLocalAlpha(true)
    }

    // TODO: 12.09.2021
    /**
     *     private val viewLocalMessageLoader: LottieAnimationView
     */


    // cached message data
    protected var chatId: Long? = null
    protected var messageId: Long? = null
    protected var quotedMessageId: Long? = null
    protected var messageLinkUrl: String? = null
    protected var messageLinkChatId: Long? = null

    // endregion


    // TODO: 13.09.2021 split on different methods to initialize views
    init {
        orientation = VERTICAL

        // header text view
        tvNewMessagesHeader = if (!hasNewMessagesTitle) {
            null
        } else {
            TextView(context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                setPadding(0, dpToPx(TOP_PADDING), 0, dpToPx(BOTTOM_PADDING))
                gravity = Gravity.CENTER_HORIZONTAL
                typeface = getFont(R.font.montserrat_bold)
                textSize = HEADER_TEXT_SIZE
                setTextColor(getColor(R.color.main_text_color))
                text = context.getString(R.string.new_messages)
                visibility = View.GONE
                this@AbstractMessageView.addView(this)
            }
        }

        // container
        layoutContainer = FrameLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            setPadding(0, dpToPx(TOP_BOTTOM_DEFAULT_PADDING), 0, dpToPx(TOP_BOTTOM_DEFAULT_PADDING))
            isClickable = true
            isFocusable = true

            // TODO: 12.09.2021
            // foreground = getDrawable(R.drawable.ripple_message)
            this@AbstractMessageView.addView(this)
        }
        _binding = ViewMessagesAttachmentBinding.inflate(LayoutInflater.from(context), layoutContainer, false)
        layoutContainer.addView(binding.root)

        binding.viewMessagesAttachmentQcmvQuotedMessage.setPadding(attachmentMarginLeft, 0, attachmentMarginRight, 0)
        binding.viewMessagesAttachmentTvSendingMessageFailed.setPadding(
            attachmentMarginLeft,
            0,
            attachmentMarginRight,
            0
        )
        binding.root.addView(messageContentView, 0, contentLayoutParams)
        // local message loader
        // TODO: 12.09.2021 add lottie animation of loader
        /* viewLocalMessageLoader = LottieAnimationView(context).apply {
             layoutParams = FrameLayout.LayoutParams(dpToPx(32), dpToPx(16)).apply {
                 topMargin = dpToPx(1)
                 rightMargin = resources.getDimension(R.dimen.message_margin_right).roundToInt()
                 gravity = Gravity.RIGHT
             }
             repeatCount = LottieDrawable.INFINITE
             repeatMode = LottieDrawable.RESTART
             setAnimation(R.raw.progress_bar)
             background = getDrawable(R.drawable.background_message_uploading)
             visibility = View.GONE
             layoutContainer.addView(this)
         }*/
        // attachments clicks
        binding.viewMessagesAttachmentQcmvQuotedMessage.setDebounceClickListener {
            quotedMessageId?.let { id -> listener?.onQuotedMessageClick(id) }
        }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(showLocalLoaderRunnable)
        removeCallbacks(showLocalAlphaRunnable)
        super.onDetachedFromWindow()
    }

    final override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
    }

    // region public

    // TODO: 13.09.2021 add documentation 
    fun setOnMessageClickListener(onClickListener: (View) -> Unit) {
        layoutContainer.setOnClickListener(onClickListener)
    }

    // TODO: 13.09.2021 add documentation 
    fun setOnMessageLongClickListener(onClickListener: (View) -> Boolean) {
        layoutContainer.setOnLongClickListener(onClickListener)
    }

    // TODO: 13.09.2021 add documentation 
    fun showNewMessagesHeader(show: Boolean) {
        tvNewMessagesHeader?.isVisible = show
    }

    // TODO: 13.09.2021 add documentation
    fun updateExtraMargins(firstInGroup: Boolean, lastInGroup: Boolean) {
        val newPaddingTop = if (firstInGroup) {
            firstInGroupMarginTop
        } else {
            0
        }
        val newPaddingBottom = if (lastInGroup) {
            lastInGroupMarginBottom
        } else {
            0
        }
        if (this.paddingTop != newPaddingTop || this.paddingBottom != newPaddingBottom) {
            this.setPadding(0, newPaddingTop, 0, newPaddingBottom)
        }
    }

    // TODO: 13.09.2021 add documentation to showing message
    fun showMessage(message: Message) {

        /**
         * Caching message information if it necessary
         */
        chatId = message.chatId
        messageId = message.id
        quotedMessageId = message.quotedMessage?.id
        messageLinkUrl = message.link?.url
        messageLinkChatId = message.link?.chatId

        /**
         * Calling showing message callback
         */
        showMessageContent(messageContentView, message)

        // TODO: 13.09.2021 if has reply message show this message and make it's layout visible
        // else make it's layout invisible
        if (message.quotedMessage != null) {
            with(binding) {
                viewMessagesAttachmentQcmvQuotedMessage.setQuotedMessage(message.quotedMessage!!)
                viewMessagesAttachmentQcmvQuotedMessage.isVisible = true
            }
        } else {
            binding.viewMessagesAttachmentQcmvQuotedMessage.isVisible = false
        }

        // TODO: 13.09.2021 split if failed or not failed sending message
        binding.viewMessagesAttachmentTvSendingMessageFailed.isVisible =
            message.isLocal && message.messageStatus == MessageStatus.Failed

        when {
            // TODO: 13.09.2021 if sending message failed hidle loader of sending image
            message.isLocal && message.messageStatus == MessageStatus.Failed -> {
                showLocalAlpha(true)
                hideLocalLoader()
            }
            message.isLocal && message.messageType == MessageType.SingleImage -> {
                // TODO: 13.09.2021 think about this method
                showLocalLoader()
                val dateDiffMs = Date().time - message.date.time
                if (dateDiffMs < 0 || dateDiffMs > LOCAL_LOADER_ALPHA_DELAY_MS) {
                    showLocalAlpha(true)
                } else {
                    showLocalAlphaDelayed(LOCAL_LOADER_ALPHA_DELAY_MS - dateDiffMs)
                }
            }
            message.isLocal -> {
                // delayed alpha, delayed loader
                // TODO: 13.09.2021 think about this method
                val dateDiffMs = Date().time - message.date.time
                if (dateDiffMs < 0 || dateDiffMs > LOCAL_LOADER_ALPHA_DELAY_MS) {
                    showLocalLoader()
                    showLocalAlpha(true)
                } else {
                    val delay = LOCAL_LOADER_ALPHA_DELAY_MS - dateDiffMs
                    showLocalLoaderDelayed(delay)
                    showLocalAlphaDelayed(delay)
                }
            }
            else -> {
                // no alpha, no loader
                hideLocalLoader()
                showLocalAlpha(false)
            }
        }
    }

    // endregion


    // region local message loader and alpha

    private fun showLocalLoader() {
        removeCallbacks(showLocalLoaderRunnable)
        // TODO: 13.09.2021 add lottie animation
        /* viewLocalMessageLoader.setVisible(true)
         viewLocalMessageLoader.resumeAnimation()*/
    }

    private fun hideLocalLoader() {
        removeCallbacks(showLocalLoaderRunnable)
        // TODO: 13.09.2021 hide lottie animation
        /* viewLocalMessageLoader.setVisible(false)
         viewLocalMessageLoader.pauseAnimation()*/
    }

    private fun showLocalLoaderDelayed(delay: Long) {
        hideLocalLoader() // hide first if was visible
        postDelayed(showLocalLoaderRunnable, delay)
    }

    private val showLocalLoaderRunnable = Runnable {
        showLocalLoader()
    }


    private fun showLocalAlpha(show: Boolean) {
        removeCallbacks(showLocalAlphaRunnable)
        val alphaValue = if (show) LOCAL_MESSAGE_ALPHA else 1.0f // TODO: 13.09.2021
        // TODO: 13.09.2021
        /* messageContentView.setAlphaIfNew(alphaValue)
         messageThreadView.setAlphaIfNew(alphaValue)*/
    }

    private fun showLocalAlphaDelayed(delay: Long) {
        showLocalAlpha(false)
        postDelayed(showLocalAlphaRunnable, delay)
    }


    // endregion


    // region highlight anim

    // TODO: update background of content without header
    fun startHighlightAnimation(startPlayTime: Long) {
        // TODO: 13.09.2021 cancel if current animation is running
        if (backgroundAnimator?.isRunning == true) {
            return
        }

        // show
        // TODO: 13.09.2021 split to other method 
        val anim1 = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, highlightColor)
        anim1.duration = HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS
        anim1.addUpdateListener { animator ->
            layoutContainer.setBackgroundColor(animator.animatedValue as Int)
        }
        // wait
        // TODO: 13.09.2021 split to other method 
        val anim2 = ValueAnimator.ofInt(0, 0)
        anim2.duration = HIGHLIGHT_WAIT_ANIM_DURATION_MS
        // hide
        // TODO: 13.09.2021 spli to other method
        val anim3 = ValueAnimator.ofObject(ArgbEvaluator(), highlightColor, Color.TRANSPARENT)
        anim3.duration = HIGHLIGHT_SHOW_HIDE_ANIM_DURATION_MS
        anim3.addUpdateListener { animator ->
            layoutContainer.setBackgroundColor(animator.animatedValue as Int)
        }
        // create set
        AnimatorSet().apply {
            playSequentially(anim1, anim2, anim3)
            addListener(
                onStart = { a -> backgroundAnimator = a as AnimatorSet },
                onCancel = { layoutContainer.setBackgroundColor(Color.TRANSPARENT) },
                onEnd = { backgroundAnimator = null })
            // TODO: implement for API 24
            // TODO: 13.09.2021 add documentation about this point
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentPlayTime = startPlayTime
            }
            start()
        }
    }

    fun clearBackgroundAnimation() {
        backgroundAnimator?.cancel()
        backgroundAnimator = null
    }


}