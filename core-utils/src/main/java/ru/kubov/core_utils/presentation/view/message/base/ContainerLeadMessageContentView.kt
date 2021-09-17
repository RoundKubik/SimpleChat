package ru.kubov.core_utils.presentation.view.message.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ViewContainerLeadMessageContentBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.utils.DateFormatter
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage

/**
 * Class implement behavior of message view that keeps content of message or image

 */
@SuppressLint("ViewConstructor")
abstract class ContainerLeadMessageContentView<CV : View> : FrameLayout {

    private var contentView: CV? = null
    private var contentViewLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

    private var dateFormatter: DateFormatter? = null

    private var _binding: ViewContainerLeadMessageContentBinding? = null
    private val binding get() = _binding!!

    private var cachedMessage: Message? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewContainerLeadMessageContentBinding.inflate(LayoutInflater.from(context), this)
        replaceStubToContentView()
    }

    /**
     * Abstract method to override showing message in message view container
     *
     * @param contentView - message view container
     * @param message - displayed message
     */
    abstract fun showMessageContent(contentView: CV?, message: Message)

    /**
     * Settings click listener on user avatar
     *
     * @param onClickListener - listener
     * implemented by [setDebounceClickListener]
     */
    fun setOnAuthorClickListener(onClickListener: (Long) -> Unit) {
        binding.viewContainerLeadMessageContentSdvAvatar.setDebounceClickListener {
            cachedMessage?.user?.id?.let {
                onClickListener.invoke(it)
            }
        }
    }

    /**
     * Settings content [Message] of message to view [contentView] and cached it into [cachedMessage]
     *
     * @param message - message displaying into view
     */
    fun showMessage(message: Message) {
        cachedMessage = message
        with(binding) {
            viewContainerLeadMessageContentViewPinOnline.isVisible =
                message.user?.online == true
            viewContainerLeadMessageContentTvAuthor.text = message.user?.name ?: message.messageAuthor.name
            viewContainerLeadMessageContentSdvAvatar.showImage(message.user?.photoUrl ?: message.messageAuthor.photoUrl)
            viewContainerLeadMessageContentTvDate.isVisible = !message.isLocal
            if (!message.isLocal) {
                binding.viewContainerLeadMessageContentTvDate.text =
                    dateFormatter?.updateDateInMessage(context, message.date)
            }
        }
        showMessageContent(contentView, message)
    }

    /**
     * Setter date formatter [DateFormatter]
     */
    fun setDateFormatter(dateFormatter: DateFormatter) {
        this.dateFormatter = dateFormatter
    }

    /**
     * Setter of [LayoutParams] of content view
     *
     * @param contentViewLayoutParams
     */
    fun setContentViewLayoutParams(contentViewLayoutParams: LayoutParams) {
        this.contentViewLayoutParams = contentViewLayoutParams
    }

    /**
     * Setter message content view
     */
    fun setMessageContentView(contentView: CV) {
        this.contentView = contentView
    }

    private fun replaceStubToContentView() {
        indexOfChild(binding.viewContainerLeadMessageContentVsViewStub).apply {
            removeViewAt(this)
            addView(contentView, this, layoutParams)
        }
    }

}