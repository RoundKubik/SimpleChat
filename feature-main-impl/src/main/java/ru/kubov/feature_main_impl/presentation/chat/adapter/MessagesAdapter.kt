package ru.kubov.feature_main_impl.presentation.chat.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.animation.addListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.presentation.view.message.base.ContainerMessageView
import ru.kubov.core_utils.presentation.view.message.chat.ChatForwardedView
import ru.kubov.core_utils.presentation.view.message.chat.ChatImageTextMessageView
import ru.kubov.core_utils.presentation.view.message.chat.ChatTextMessageView
import ru.kubov.core_utils.presentation.view.message.simple.SimpleForwardedMessageView
import ru.kubov.core_utils.presentation.view.message.simple.SimpleImageTextMessageView
import ru.kubov.core_utils.presentation.view.message.simple.SimpleTextMessageView
import ru.kubov.feature_main_impl.R
import ru.kubov.feature_main_impl.databinding.ViewMessageProgressBarLoaderBinding
import kotlin.math.roundToInt

/**
 * Class provides logic of presentation messages in chat
 * Main type of showed message is [ContainerMessageView]. Also it provides payloads for messages
 *
 * @param context - base context
 * @param onMessageClickListener - listener if was clicking on message view
 * @param onMessageLongClickListener - listener if was long click on message view
 * @param onAuthorClickListener - listener of user click on author (avatar or nickname)
 * @param messageClickListener - listener of user click on message
 * @param forwardedMessageListener - listener of forwarded message if user click on it
 * @param forwardedImageClickListener - listener of image clicking in forwarded message
 * @param onLoadAboveListener - listener for notification loading above loader messages
 * @param onLoadBelowListener - listener for notification loading below loader messages
 * @param onMessageBindedListener - listener for notification if message was binded into current view holder
 */
class MessagesAdapter(
    context: Context,
    private val onMessageClickListener: ((message: Message) -> Unit)? = null,
    private val onMessageLongClickListener: ((message: Message) -> Unit)? = null,
    private val onAuthorClickListener: ((userId: Long) -> Unit)? = null,
    private val messageClickListener: ((chatId: Long, messageId: Long) -> Unit)? = null,
    private val forwardedMessageListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null,
    private val forwardedImageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null,
    private val onLoadAboveListener: (() -> Unit)? = null,
    private val onLoadBelowListener: (() -> Unit)? = null,
    private val onMessageBindedListener: ((message: Message) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Class provides payload properties
     */
    sealed class Payload {
        object Highlight : Payload()
        data class CanSendMessages(val can: Boolean) : Payload()
    }

    /**
     * Class provides static fields
     *
     * [TYPE_LOADER] - type of loader view provides binding loader layout into [LoaderViewHolder]
     *
     * [TYPE_SIMPLE_MESSAGE_TEXT] -type of simple message view with text and without any user avatar or nickname,
     * by this type will be bind in [NewMessageViewHolder] instance of [SimpleTextMessageView]
     *
     *  [TYPE_MESSAGE_TEXT] -type of  message view with text and with user avatar and nickname,
     * by this type will be bind in [NewMessageViewHolder] instance of [ChatTextMessageView]
     *
     *  [TYPE_SIMPLE_MESSAGE_IMAGE] -type of simple message view with text and image or image without text,
     *  also it is without any user avatar or nickname, by this type will be bind in [NewMessageViewHolder]
     *  instance of [SimpleImageTextMessageView]
     *
     *  [TYPE_MESSAGE_IMAGE] -type of message view with text and image or image without text,
     *  also it has user avatar or nickname, by this type will be bind in [NewMessageViewHolder]
     *  instance of [ChatImageTextMessageView]
     *
     *  [TYPE_SIMPLE_MESSAGE_FORWARDED] -type of simple message view that keeps [ImageContentView] or
     *  [ImageTextContentView] without any user avatar or nickname, by this type will be bind in
     *  [NewMessageViewHolder] instance of [SimpleTextMessageView]
     *
     *  [TYPE_MESSAGE_FORWARDED] -type of message view that keeps [ChatImageTextMessageView] or
     *  [ChatTextMessageView] with user avatar or nickname, by this type will be bind in
     *  [NewMessageViewHolder] instance of [SimpleTextMessageView]
     *
     *  [SAME_GROUP_MAX_TIME_MS] - value keeps time difference in message groups in milliseconds
     */
    companion object {
        private const val TYPE_LOADER = 0
        private const val TYPE_SIMPLE_MESSAGE_TEXT = 1
        private const val TYPE_MESSAGE_TEXT = 2
        private const val TYPE_SIMPLE_MESSAGE_IMAGE = 3
        private const val TYPE_MESSAGE_IMAGE = 4
        private const val TYPE_SIMPLE_MESSAGE_FORWARDED = 5
        private const val TYPE_MESSAGE_FORWARDED = 6

        private const val SAME_GROUP_MAX_TIME_MS = 120_000L

        private const val TAG = "MessagesAdapter"
    }


    /**
     * Propery keeps value of possibility sending message
     */
    var canSendMessages: Boolean = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            notifyItemRangeChanged(0, itemCount, Payload.CanSendMessages(value))
        }

    /**
     * Property keeps values of messages sent into this adapter
     */
    var messages: List<Message>? = null
        set(newMessages) {
            if (field.isNullOrEmpty() || newMessages == null) {
                field = newMessages
                notifyDataSetChanged()
                return
            }

            val diffCallback = MessageDiffCallback(field!!, newMessages)
            val diffResult = DiffUtil.calculateDiff(diffCallback, false)
            field = newMessages
            diffResult.dispatchUpdatesTo(this)
        }


    /**
     * Property with id of lsat unread id, used for showing new message title in message
     * views if user still hasn't looked at the message
     */
    var firstUnreadMessageId: Long? = null
        set(newId) {
            if (field == newId) return

            val oldId = field
            field = newId

            oldId?.let { id -> notifyItemChanged(findMessagePosition(id)) }
            newId?.let { id -> notifyItemChanged(findMessagePosition(id)) }
        }

    /**
     * Property keeps info if there is visibility of content above loader
     */
    var aboveLoaderVisible: Boolean = false
        set(visible) {
            if (visible == field) return
            val oldPosition = aboveLoaderPosition
            field = visible
            if (visible) {
                notifyItemInserted(aboveLoaderPosition)
            } else {
                notifyItemRemoved(oldPosition)
            }
        }

    /**
     * Property with count of messages in current adapter
     * returns value size of [messages]
     */
    val messagesCount: Int
        get() = messages?.size ?: 0

    private val aboveLoaderPosition: Int
        get() = if (aboveLoaderVisible) messagesCount else RecyclerView.NO_POSITION

    private val messageViewFactory = MessageViewFactory(context)

    private val highlightAnimMap: MutableMap<Long, ValueAnimator> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_LOADER -> {
                val binding =
                    ViewMessageProgressBarLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoaderViewHolder(binding)
            }
            else -> {
                Log.d(TAG, "onCreateViewHolder: ")
                NewMessageViewHolder(messageViewFactory.createView(viewType))
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewMessageViewHolder -> {
                Log.d(TAG, "onBindViewHolder: $holder")
                val message = getItem(position)
                val showNewMessagesHeader = firstUnreadMessageId == message.id
                holder.messageView.showNewMessagesHeader(showNewMessagesHeader)
                holder.bindMessage(message, canSendMessages)
                /* val anim = highlightAnimMap[message.id]
                 if (anim != null && anim.isRunning) {
                     holder.messageView.startHighlightAnimation(anim.currentPlayTime)
                 } else {
                     holder.messageView.clearBackgroundAnimation()
                 }*/
                onMessageBindedListener?.invoke(message)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (holder is NewMessageViewHolder && payloads.isNotEmpty()) {
            payloads.forEach { payload ->
                when (payload) {
                    is Payload.Highlight -> {
                        val message = getItem(position)
                        val anim = highlightAnimMap[message.id]
                        if (anim != null && anim.isRunning) {
                            holder.messageView.startHighlightAnimation(anim.currentPlayTime)
                        } else {
                            holder.messageView.clearBackgroundAnimation()
                        }
                    }
                    is Payload.CanSendMessages -> {
                        val message = getItem(position)
                        holder.setCanReply(canSendMessages && !message.isLocal)
                    }
                }
            }
        } else super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int =
        messagesCount + (if (aboveLoaderVisible) 1 else 0)

    override fun getItemViewType(position: Int): Int {
        if (position == aboveLoaderPosition) {
            return TYPE_LOADER
        }

        val message = getItem(position)
        val firstInGroup = isFirstInGroup(position)
        return when (message.messageType) {
            MessageType.Text -> if (firstInGroup) TYPE_MESSAGE_TEXT else TYPE_SIMPLE_MESSAGE_TEXT
            MessageType.SingleImage -> if (firstInGroup) TYPE_MESSAGE_IMAGE else TYPE_SIMPLE_MESSAGE_IMAGE
            MessageType.Forward -> if (firstInGroup) TYPE_MESSAGE_FORWARDED else TYPE_SIMPLE_MESSAGE_IMAGE
        }
    }

    /**
     * Provides check if message first in group to. It could be used for creation groups of messages like chat in VK
     * First in group message will be has author avatar and nickname and be based on ContainerLeadMessageContentView
     * @param position - of checked message
     */
    fun isFirstInGroup(position: Int): Boolean {
        val message = getItem(position)
        val prevMessage = if (position < messagesCount - 1) {
            getItem(position + 1)
        } else {
            null
        }
        return prevMessage == null ||
                message.id == firstUnreadMessageId ||
                message.messageAuthor.userId != prevMessage.messageAuthor.userId ||
                (message.date.time - prevMessage.date.time) > SAME_GROUP_MAX_TIME_MS
    }

    /**
     * Provides finding message position in adapter by id
     * @param messageId - id of message
     */
    fun findMessagePosition(messageId: Long): Int {
        val i = messages?.indexOfFirst { m -> m.id == messageId } ?: -1
        return if (i != -1) {
            i
        } else {
            RecyclerView.NO_POSITION
        }
    }

    /**
     * Provides animation of highlighting message when message is loading
     * @param messageId - id of highlited message
     */
    fun highlightMessage(messageId: Long) {
        if (highlightAnimMap[messageId] != null) {
            return
        }

        ValueAnimator.ofInt(0, 0).apply {
            duration = ContainerMessageView.HIGHLIGHT_ANIM_DURATION_MS
            addListener(
                onStart = { highlightAnimMap[messageId] = this@apply },
                onEnd = { highlightAnimMap.remove(messageId) })
            start()
        }

        val position = findMessagePosition(messageId)
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position, Payload.Highlight)
        }
    }

    private fun getItem(position: Int): Message {
        if (messages == null) {
            throw NullPointerException("Empty messages list")
        }

        val message = messages!![position]
        if (position == 0) {
            onLoadAboveListener?.invoke()
        }
        if (position == messages!!.size - 1) {
            onLoadBelowListener?.invoke()
        }
        return message
    }

    /**
     * Class implements base logic of creation view of message item in [RecyclerView]
     * @param messageView - message view that provides by [MessageViewFactory]
     */
    inner class NewMessageViewHolder(val messageView: ContainerMessageView<*>) :
        RecyclerView.ViewHolder(messageView) {

        /**
         * Cached value of message chat id
         */
        var chatId: Long? = null
            private set

        /**
         * Cached value of message  id
         */
        var messageId: Long? = null
            private set

        /**
         * Cached value of possibility reply message
         */
        var canReply: Boolean = false
            private set

        init {
            messageView.setOnMessageClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val message = getItem(position)
                    onMessageClickListener?.invoke(message)
                }
            }
            messageView.setOnMessageLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val message = getItem(position)
                    onMessageLongClickListener?.invoke(message)
                }
                return@setOnMessageLongClickListener true
            }
        }

        /**
         * Method provides bind data in message view
         *
         * @param message - message data for binding in view
         * @param canSendMessages - value keeps value of possibility sending message
         */
        fun bindMessage(message: Message, canSendMessages: Boolean) {
            chatId = message.chatId
            messageId = message.id
            canReply = canSendMessages && !message.isLocal
            Log.d(TAG, "bindMessage: $message")
            messageView.showMessage(message)
        }

        /**
         * Method provides setting value of possibility reply message
         *
         * @param canReply - value of possibility reply message
         */
        fun setCanReply(canReply: Boolean) {
            this.canReply = canReply
        }
    }

    /**
     * Class implements base logic of creation view of loader item in [RecyclerView]
     * @param binding - viewbinding of message loader
     */
    inner class LoaderViewHolder(binding: ViewMessageProgressBarLoaderBinding) :
        RecyclerView.ViewHolder(binding.root)


    /**
     * Message factory provides creation of chat messages
     * @param context - base context
     * @param onAuthorClickListener - listener of user click on author (avatar or nickname)
     * @param messageClickListener - listener of user click on message
     * @param forwardedMessageListener - listener of forwarded message if user click on it
     * @param forwardedImageClickListener - listener of image clicking in forwarded message
     */
    private class MessageViewFactory(
        private val context: Context,
        private val onAuthorClickListener: ((userId: Long) -> Unit)? = null,
        private val messageClickListener: ((chatId: Long, messageId: Long) -> Unit)? = null,
        private val forwardedMessageListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null,
        private val forwardedImageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null
    ) {

        private val leftMarginsSimpleMessageView = context.resources.getDimension(R.dimen.dp_64).roundToInt()
        private val topMarginsSimpleMessageView = context.resources.getDimension(R.dimen.dp_8).roundToInt()
        private val rightMarginsSimpleMessageView = context.resources.getDimension(R.dimen.dp_12).roundToInt()
        private val bottomMarginsSimpleMessageView = context.resources.getDimension(R.dimen.dp_0).roundToInt()

        /**
         * Factory method provides creation view by it's type
         * @param viewType - type of view, see companion object of global class
         */
        fun createView(viewType: Int): ContainerMessageView<*> {
            val messageView = when (viewType) {
                TYPE_SIMPLE_MESSAGE_TEXT -> {
                    Log.d(TAG, "createView: $TYPE_SIMPLE_MESSAGE_TEXT")
                    SimpleTextMessageView(context).apply {
                        setAttachmentPaddings(
                            leftMarginsSimpleMessageView,
                            topMarginsSimpleMessageView,
                            rightMarginsSimpleMessageView,
                            bottomMarginsSimpleMessageView,
                        )
                    }
                }
                TYPE_MESSAGE_TEXT -> {
                    Log.d(TAG, "createView: $TYPE_MESSAGE_TEXT")
                    ChatTextMessageView(context).apply {
                        onMessageAuthorClickListener = onAuthorClickListener

                    }
                }
                TYPE_SIMPLE_MESSAGE_IMAGE -> SimpleImageTextMessageView(context).apply {
                    onMessageClickListener = messageClickListener
                    setAttachmentPaddings(
                        leftMarginsSimpleMessageView,
                        topMarginsSimpleMessageView,
                        rightMarginsSimpleMessageView,
                        bottomMarginsSimpleMessageView,
                    )
                }
                TYPE_MESSAGE_IMAGE -> ChatImageTextMessageView(context).apply {
                    onMessageClickListener = messageClickListener
                }
                TYPE_SIMPLE_MESSAGE_FORWARDED -> SimpleForwardedMessageView(context).apply {
                    setForwardedMessageClickListener(forwardedMessageListener)
                    setOnImageClickListener(forwardedImageClickListener)
                    setAttachmentPaddings(
                        leftMarginsSimpleMessageView,
                        topMarginsSimpleMessageView,
                        rightMarginsSimpleMessageView,
                        bottomMarginsSimpleMessageView,
                    )
                }
                TYPE_MESSAGE_FORWARDED -> ChatForwardedView(context).apply {
                    setForwardedMessageClickListener(forwardedMessageListener)
                    setOnImageClickListener(forwardedImageClickListener)
                }
                else -> throw IllegalArgumentException("Not supported viewType value: $viewType")
            }
            messageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return messageView
        }

    }

}
