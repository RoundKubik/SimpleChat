package ru.kubov.core_utils.presentation.view.message.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ViewContainerLeadMessageContentBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.utils.DateFormatter
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage
import javax.inject.Inject

/**
 * Class implement behavior of message view that keeps content of message or image
 *
 * @param context - context to create view
 * @param layoutParams - params of [contentView]
 * @param contentView - view displayed into this message view container
 * @param dateFormatter - date formatter convert date in native format to human readable format
 */
@SuppressLint("ViewConstructor")
abstract class ContainerLeadMessageContentView<CV : View>(
    context: Context,
    private val layoutParams: LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT),
    private val contentView: CV,
    private val dateFormatter: DateFormatter
) : FrameLayout(context, null, 0) {

    private var _binding: ViewContainerLeadMessageContentBinding? = null
    private val binding get() = _binding!!

    private var cachedMessage: Message? = null

    init {
        _binding = ViewContainerLeadMessageContentBinding.inflate(LayoutInflater.from(context), this)
        replaceStubToContentView()
    }

    /**
     * Abstract method to override showing message in message view container
     *
     * @param contentView - message view container
     * @param message - displayed message
     */
    abstract fun showMessageContent(contentView: CV, message: Message)

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
                    dateFormatter.updateDateInMessage(context, message.date)
            }
        }
        showMessageContent(contentView, message)
    }

    private fun replaceStubToContentView() {
        indexOfChild(binding.viewContainerLeadMessageContentVsViewStub).apply {
            removeViewAt(this)
            addView(contentView, this, layoutParams)
        }
    }

}