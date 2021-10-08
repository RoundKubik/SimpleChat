package com.kubov.core_ui.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.kubov.core_ui.R
import com.kubov.core_ui.databinding.ViewChatInputBinding
import com.kubov.core_ui.presentation.view.message.MessageReplyView
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.utils.TextChangesTextWatcher

/**
 * Class implements presentation of view with input message
 */
class ChatInputView : LinearLayout {

    interface Listener {
        fun onSendMessageClick(text: String)
        fun onPhotoPickerClick()
        fun onMessageTextChanged(text: String)
        fun onMessageSelectionChanged(text: String, cursorIndex: Int)
        fun onDeleteClick()
    }

    private var _binding: ViewChatInputBinding? = null
    private val binding get() = _binding!!

    private var listener: Listener? = null

    private val textWatcher = TextChangesTextWatcher(this::onTextChanged)
    private var messageReplyView: MessageReplyView? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        _binding = ViewChatInputBinding.inflate(LayoutInflater.from(context), this)
        orientation = VERTICAL
        setBackgroundResource(R.color.background_secondary)

        binding.viewChatInputIvSendMessage.setDebounceClickListener {
            val text = binding.viewChatInputEtMessageInput.text.toString().trim()
            if (text.isNotBlank()) {
                listener?.onSendMessageClick(text)
            }
        }
        binding.viewChatInputIvTakePicture.setDebounceClickListener {
            listener?.onPhotoPickerClick()
        }
        binding.viewChatInputEtMessageInput.onSelectionChangeListener = { cursorIndex ->
            listener?.onMessageSelectionChanged(binding.viewChatInputEtMessageInput.text.toString(), cursorIndex)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.viewChatInputEtMessageInput.post {
            binding.viewChatInputEtMessageInput.addTextChangedListener(textWatcher)
        }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(null)
        binding.viewChatInputEtMessageInput.removeCallbacks(null)
        binding.viewChatInputEtMessageInput.removeTextChangedListener(textWatcher)
        super.onDetachedFromWindow()
    }

    private fun onTextChanged(inputText: CharSequence) {
            val text = inputText.toString().trim()
            listener?.onMessageTextChanged(text)
    }

    /**
     * Set input text
     */
    fun setInputText(text: String) {
        binding.viewChatInputEtMessageInput.setText(text)
    }

    /**
     * Return trimmed input text.
     */
    fun getInputText(): String = binding.viewChatInputEtMessageInput.text.toString().trim()

    /**
     * Clear text from message input
     */
    fun clearInputText() {
        binding.viewChatInputEtMessageInput.setText(String())
    }

    /**
     * Insert string by current cursor position.
     */
    fun insertStringAtCursor(str: String) {
        val cursorIndex = binding.viewChatInputEtMessageInput.selectionStart
        val sourceText = binding.viewChatInputEtMessageInput.text

        with(binding.viewChatInputEtMessageInput) {
            this.text = sourceText?.insert(cursorIndex, str)
            setSelection(cursorIndex + str.length)
        }
    }

    /**
     * Method to show reply message and show message content
     * @param message
     */
    fun showRepliedMessage(message: Message) {
        if (messageReplyView == null) {
            val position = indexOfChild(binding.viewChatInputVsReplyMessage)
            removeView(binding.viewChatInputVsReplyMessage)
            messageReplyView = MessageReplyView(context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0)
                setOnRemoveClickListener {
                    listener?.onDeleteClick()
                }
                onHideAnimationEndListener = this@ChatInputView::onHideAnimationEnd
                this@ChatInputView.addView(this, position)
            }
        }
        messageReplyView?.isVisible = true
        messageReplyView?.animateShow()
        messageReplyView?.showMessage(message)
    }

    /**
     * Method to hide reply message
     */
    fun removeRepliedMessage() {
        messageReplyView?.animateHide()
    }

    private fun onHideAnimationEnd() {
        binding.viewChatInputVsReplyMessage.isVisible = false
    }
}