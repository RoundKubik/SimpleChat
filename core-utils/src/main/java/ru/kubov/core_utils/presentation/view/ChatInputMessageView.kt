package com.capture.tech.views.input

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.capture.tech.R
import com.capture.tech.core.models.Gif
import com.capture.tech.core.models.Message
import com.capture.tech.core.util.AndroidScreen
import com.capture.tech.extensions.*
import com.capture.tech.utils.TextChangesTextWatcher
import com.capture.tech.views.input.gif.GifInputView
import kotlinx.android.synthetic.main.view_chat_input.view.*
import kotlin.math.min
import kotlin.math.roundToLong


class ChatInputView : LinearLayout {

    companion object {
        private const val HIDE_BUTTON_ANIM_DURATION_MS = 75L
        private const val SHOW_BUTTON_ANIM_DURATION_MS = 200L
        private const val SEND_BUTTON_MIN_ALPHA = 0.5f
        private const val SEND_BUTTON_MIN_SCALE = 0.75f
    }

    private sealed class InputType {
        object Message : InputType()
        object Gifs : InputType()
    }

    interface Listener {
        fun onSendMessageClick(text: String)
        fun onPhotoPickerClick()
        fun onMessageTextChanged(text: String)
        fun onMessageSelectionChanged(text: String, cursorIndex: Int)
        fun onDeleteClick()
        fun onGifQueryChanged(query: String)
        fun onGifClick(gif: Gif, query: String)
    }

    // region properties

    var listener: Listener? = null
    val gifsVisible: Boolean
        get() = inputType == InputType.Gifs

    private var gifInputView: GifInputView? = null
    private var messageReplyView: MessageReplyView? = null

    private val textWatcher = TextChangesTextWatcher(this::onTextChanged)
    private var ignoreChangesListener = false

    private var sendButtonsAnimator: ValueAnimator? = null
    private var latestAnimationActive: Boolean? = null

    private var navigationBarHeight: Int = 0
    var keyboardHeight: Int = AndroidScreen.keyboardHeight(context)

    private val layoutBottomInsetVisible: Boolean
        get() = layoutBottomInset.height > 0

    // endregion


    // region init

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        View.inflate(context, R.layout.view_chat_input, this)
        orientation = VERTICAL
        setBackgroundResource(R.color.background)
        // clicks
        ivGifInput.setOnClickListener {
            inputType = InputType.Gifs
            showKeyboard()
        }
        ivSendMessageActive.setOnClickListener {
            val text = etMessageInput.string().trim()
            if (!text.isBlank()) listener?.onSendMessageClick(text)
        }
        // ivChangeInput.setOnClickListener { onChangeInputClick() }
        ivPhotoPicker.setOnClickListener { listener?.onPhotoPickerClick() }
        // initial view state
        inputType = InputType.Message
        keyboardVisible = false
        onKeyboardVisibleChanged() // has to be called manually
        val isTextEmpty = etMessageInput.text.toString().isEmpty()
        setSendMessageActive(!isTextEmpty)
        // setChangeInputVisibility(isTextEmpty)
        etMessageInput.onSelectionChangeListener = { cursorIndex ->
            listener?.onMessageSelectionChanged(etMessageInput.string(), cursorIndex)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnApplyWindowInsetsListener { _, insets ->
            // add constant navigation bar inset, save and update keyboard inset
            val bottomInset = insets.systemWindowInsetBottom
            val newKeyboardVisible = bottomInset >= dpToPx(AndroidScreen.KEYBOARD_MIN_POSSIBLE_HEIGHT_DP)
            when {
                !newKeyboardVisible && bottomInset != navigationBarHeight -> {
                    if (bottomInset != navigationBarHeight) {
                        navigationBarHeight = bottomInset
                        if (paddingBottom != navigationBarHeight) updatePadding(bottom = navigationBarHeight)
                    }
                }
                newKeyboardVisible && keyboardHeight != bottomInset - navigationBarHeight -> {
                    keyboardHeight = bottomInset - navigationBarHeight
                    if (layoutBottomInsetVisible) showBottomInset(true) // update height
                }
            }
            keyboardVisible = newKeyboardVisible
            insets
        }
        requestApplyInsets()

        etMessageInput.post { etMessageInput.addTextChangedListener(textWatcher) }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(null)
        etMessageInput.removeCallbacks(null)
        etMessageInput.removeTextChangedListener(textWatcher)
        sendButtonsAnimator?.cancel()
        sendButtonsAnimator = null
        latestAnimationActive = null
        super.onDetachedFromWindow()
    }

    private fun onTextChanged(inputText: CharSequence) {
        if (!ignoreChangesListener) {
            val text = inputText.toString().trim()
            if (text.isNotBlank()) animateSendMessageActive()
            else animateSendMessageInactive()
            listener?.onMessageTextChanged(text)
        }
    }

    // endregion


    // region public

    /** Set input text without animation and triggering listener. */
    fun setInputText(text: String) {
        ignoreChangesListener = true
        etMessageInput.setText(text)
        setSendMessageActive(text.isNotEmpty())
        // setChangeInputVisibility(text.isEmpty())
        ignoreChangesListener = false
    }

    /** Return trimmed input text. */
    fun getInputText(): String = etMessageInput.string().trim()

    fun clearInputText() {
        etMessageInput.setText("")
    }

    /** Insert string by current cursor position. */
    fun insertStringAtCursor(str: String) {
        val cursorIndex = etMessageInput.selectionStart
        val sourceText = etMessageInput.text
        etMessageInput.text = sourceText.insert(cursorIndex, str)
        etMessageInput.setSelection(cursorIndex + str.length)
    }

    // endregion


    // region replied message

    fun showRepliedMessage(message: Message) {
        if (messageReplyView == null) {
            // replace view stub by real view at specific position
            val position = indexOfChild(viewStubMessageReply)
            removeView(viewStubMessageReply)
            messageReplyView = MessageReplyView(context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0)
                setOnRemoveClickListener(OnClickListener { listener?.onDeleteClick() })
                onHideAnimationEndListener = this@ChatInputView::onHideAnimationEnd
                this@ChatInputView.addView(this, position)
            }
        }
        messageReplyView!!.setVisible(true)
        messageReplyView!!.animateShow()
        messageReplyView!!.showMessage(message)
    }

    fun removeRepliedMessage() {
        messageReplyView?.animateHide()
    }

    private fun onHideAnimationEnd() {
        messageReplyView?.setVisible(false)
    }

    // endregion


    // region keyboard

    fun showKeyboard(delay: Long = 0) {
        if (delay <= 0) showKeyboardInternal()
        else postDelayed({ showKeyboardInternal() }, delay)
    }

    private fun showKeyboardInternal() {
        if (inputType == InputType.Message) {
            etMessageInput.requestFocus()
            etMessageInput.showKeyboard()
        } else {
            gifInputView?.requestInputFocus()
            gifInputView?.showInputKeyboard()
        }
    }

    private var keyboardVisible: Boolean
        set(visible) {
            if (field == visible) return
            field = visible
            onKeyboardVisibleChanged()
        }

    private fun onKeyboardVisibleChanged() {
        if (keyboardVisible) {
            // showKeyboard()
            showBottomInset(true)
        } else {
            // hideKeyboard()
            showBottomInset(false)
            clearFocus()
        }
    }

    private fun showBottomInset(visible: Boolean) {
        layoutBottomInset.updateLayoutParams { height = if (visible) keyboardHeight else 0 }
    }




    private var inputType: InputType
        set(newType) {
            if (newType == inputType) return
            field = newType

            when (newType) {
                InputType.Message -> {
                    gifInputView?.setVisible(false)
                    gifInputView?.clearInput()
                    layoutMessage.setVisible(true)
                    if (keyboardVisible) etMessageInput.requestFocus()
                    else clearFocus()
                }



}