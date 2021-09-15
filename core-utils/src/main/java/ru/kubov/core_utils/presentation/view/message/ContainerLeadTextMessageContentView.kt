package ru.kubov.core_utils.presentation.view.message

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ViewContainerLeadMessageContentBinding
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage

// TODO: 14.09.2021 add flexible obtaining res from xml
@SuppressLint("ViewConstructor")
abstract class ContainerLeadTextMessageContentView<CV : View>(
    context: Context,
    private val layoutParams: LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT),
    private val contentView: CV
) : FrameLayout(context, null, 0) {

    private var _binding: ViewContainerLeadMessageContentBinding? = null
    private val binding get() = _binding!!

    private var cachedMessage: Message? = null

    abstract fun showMessageContent(contentView: CV, message: Message)

    init {
        _binding = ViewContainerLeadMessageContentBinding.inflate(LayoutInflater.from(context), this)
        replaceStubToContentView()
    }

    fun setOnAuthorClickListener(onClickListener: (Long) -> Unit) {
        binding.viewContainerLeadMessageContentSdvAvatar.setDebounceClickListener {
            cachedMessage?.user?.id?.let {
                onClickListener.invoke(it)
            }
        }
    }

    fun showMessage(message: Message) {
        cachedMessage = message
        with(binding) {
            viewContainerLeadMessageContentViewPinOnline.isVisible =
                message.user?.online == true
            viewContainerLeadMessageContentTvAuthor.text = message.user?.name ?: message.messageAuthor.name
            viewContainerLeadMessageContentSdvAvatar.showImage(message.user?.photoUrl ?: message.messageAuthor.photoUrl)
            viewContainerLeadMessageContentTvDate.isVisible = !message.isLocal

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