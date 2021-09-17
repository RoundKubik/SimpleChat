package ru.kubov.feature_main_impl.presentation.chat.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kubov.core_utils.domain.models.Message
import ru.kubov.core_utils.domain.models.MessageType
import ru.kubov.core_utils.extensions.dpToPx
import ru.kubov.core_utils.presentation.view.message.base.ContainerMessageView
import ru.kubov.core_utils.presentation.view.message.chat.ChatForwardedView
import ru.kubov.core_utils.presentation.view.message.chat.ChatImageTextMessageView
import ru.kubov.core_utils.presentation.view.message.chat.ChatTextMessageView
import ru.kubov.feature_main_impl.R

// TODO: Extend and implement adapter for channels

/** Adapter for list of messages. */
// TODO: 13.09.2021 split and add documentation
// TODO: 13.09.2021 in next versions extends to supporting of channels or comments of messages like thread in twitter
class MessagesAdapter(
    context: Context,
    private val pageListener: PageListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // TODO: 13.09.2021 add documentation
    interface PageListener {
        fun onLoadAbove()
        fun onLoadBelow()
        fun onMessageBinded(message: Message)
    }

    // TODO: 13.09.2021  add documentation
    sealed class Payload {
        object Highlight : Payload()
        data class CanSendMessages(val can: Boolean) : Payload()
    }

    // TODO: 13.09.2021 add documentation on this constants
    companion object {
        private const val TYPE_LOADER = 0
        private const val TYPE_MESSAGE_TEXT = 1
        private const val TYPE_MESSAGE_IMAGE = 2
        private const val TYPE_MESSAGE_FORWARDED = 3

        private const val SAME_GROUP_MAX_TIME_MS = 120_000L
    }

    private val messageViewFactory = MessageViewFactory(context)

    private val highlightAnimMap: MutableMap<Long, ValueAnimator> = HashMap()


    var canSendMessages: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            notifyItemRangeChanged(0, itemCount, Payload.CanSendMessages(value))
        }

    var userPhotoUrl: String? = null
        set(value) {
            if (field == value) return
            field = value
        }

    var messages: List<Message>? = null
        set(newMessages) {
            // no diff needed
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

    var firstUnreadMessageId: Long? = null
        set(newId) {
            if (field == newId) return

            val oldId = field
            field = newId

            // update item TODO: add MessagesChangedPayload payload
            oldId?.let { id -> notifyItemChanged(findMessagePosition(id)) }
            newId?.let { id -> notifyItemChanged(findMessagePosition(id)) }
        }

    var aboveLoaderVisible: Boolean = false
        set(visible) {
            if (visible == field) return
            val oldPosition = aboveLoaderPosition
            field = visible
            // show and hide loader
            if (visible) notifyItemInserted(aboveLoaderPosition)
            else notifyItemRemoved(oldPosition)
        }

    var hasLatestMessage: Boolean = false // whether list of messages has latest message or not

    // endregion


    // region recycler view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_LOADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_page_loader, parent, false)
                LoaderViewHolder(view)
            }
            else -> NewMessageViewHolder(messageViewFactory.createView(viewType))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewMessageViewHolder -> {
                val message = getItem(position)
                // show new messages title
                val showNewMessagesHeader = firstUnreadMessageId == message.id
                holder.messageView.showNewMessagesHeader(showNewMessagesHeader)
                holder.bindMessage(message, canSendMessages)
                // update animation
                val anim = highlightAnimMap[message.id]
                if (anim != null && anim.isRunning) holder.messageView.startHighlightAnimation(anim.currentPlayTime)
                else holder.messageView.clearBackgroundAnimation()
                // listener
                pageListener.onMessageBinded(message)
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
                        if (anim != null && anim.isRunning) holder.messageView.startHighlightAnimation(anim.currentPlayTime)
                        else holder.messageView.clearBackgroundAnimation()
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
        messagesCount + // messages
                (if (aboveLoaderVisible) 1 else 0) // loader

    override fun getItemViewType(position: Int): Int {
        if (position == aboveLoaderPosition) return TYPE_LOADER

        val message = getItem(position)
        val firstInGroup = isFirstInGroup(position)
        return when (message.messageType) {
            MessageType.Text -> TYPE_MESSAGE_TEXT
            MessageType.SingleImage -> TYPE_MESSAGE_IMAGE
            MessageType.Forward -> TYPE_MESSAGE_FORWARDED
        }
    }

    // endregion


    // region private

    val messagesCount: Int
        get() = messages?.size ?: 0

    private val aboveLoaderPosition: Int
        get() = if (aboveLoaderVisible) messagesCount else RecyclerView.NO_POSITION

    private fun getItem(position: Int): Message {
        if (messages == null) throw NullPointerException("list of messages is null")
        if (position < 0 || position >= messages!!.size) throw IndexOutOfBoundsException("Index = $position, size = ${messages!!.size}")

        val message = messages!![position]
        if (position == 0) pageListener.onLoadBelow()
        if (position == messages!!.size - 1) pageListener.onLoadAbove()
        return message
    }

    fun isFirstInGroup(position: Int): Boolean {
        val message = getItem(position)
        val prevMessage = if (position < messagesCount - 1) getItem(position + 1) else null
        return prevMessage == null ||
                message.id == firstUnreadMessageId || // message has 'New messages' title
                message.messageAuthor.userId != prevMessage.messageAuthor.userId || // previous message from another author
                (message.date.time - prevMessage.date.time) > SAME_GROUP_MAX_TIME_MS // enough time passed
    }

    fun isLastInGroup(position: Int): Boolean {
        val message = getItem(position)
        val nextMessage = if (position > 0) getItem(position - 1) else null
        return nextMessage == null ||
                nextMessage.id == firstUnreadMessageId || // next message has 'New messages' title
                message.messageAuthor.userId != nextMessage.messageAuthor.userId || // next message from another author
                (nextMessage.date.time - message.date.time) > SAME_GROUP_MAX_TIME_MS // enough time passed
    }

    // endregion


    // region public

    fun findMessage(adapterPosition: Int): Message? =
        if (adapterPosition < messagesCount) getItem(adapterPosition) else null

    fun findMessagePosition(messageId: Long): Int {
        val i = messages?.indexOfFirst { m -> m.id == messageId } ?: -1
        return if (i != -1) i else RecyclerView.NO_POSITION
    }

    fun highlightMessage(messageId: Long) {
        if (highlightAnimMap[messageId] != null) return

        ValueAnimator.ofInt(0, 0).apply {
            duration = ContainerMessageView.HIGHLIGHT_ANIM_DURATION_MS
            addListener(
                onStart = { highlightAnimMap[messageId] = this@apply },
                onEnd = { highlightAnimMap.remove(messageId) })
            start()
        }

        // call to update currently visible item
        val position = findMessagePosition(messageId)
        if (position != RecyclerView.NO_POSITION) notifyItemChanged(position, Payload.Highlight)
    }

    // endregion


    // region view holders

    inner class NewMessageViewHolder(val messageView: ContainerMessageView<*>) : RecyclerView.ViewHolder(messageView) {

        var chatId: Long? = null
            private set
        var messageId: Long? = null
            private set
        var canReply: Boolean = false
            private set

        init {
            messageView.setOnMessageClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val message = getItem(position)
                    messageListener.onMessageClick(message)
                }
            }
            messageView.setOnMessageLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val message = getItem(position)
                    messageListener.onMessageLongClick(message)
                }
                return@setOnMessageLongClickListener true
            }
            messageView.setOnThreadClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val message = getItem(position)
                    messageListener.onThreadClick(message)
                }
            }
        }

        fun bindMessage(message: Message, canSendMessages: Boolean) {
            chatId = message.chatId
            messageId = message.id
            canReply = canSendMessages && !message.isLocal
            messageView.showMessage(message)
            val showThread = (message.thread?.messagesCount ?: 0) > 0
            when {
                showThread -> messageView.showMessageThread(message.thread!!)
                showStartThread -> messageView.showStartThread(userPhotoUrl)
                else -> messageView.hideMessageThread()
            }
        }

        fun setCanReply(canReply: Boolean) {
            this.canReply = canReply
        }

    }

    inner class LoaderViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // endregion


    // Add documentation
    private class MessageViewFactory(
        private val context: Context,
        private val onAuthorClickListener: ((userId: Long) -> Unit)? = null,
        private val messageClickListener: ((chatId: Long, messageId: Long) -> Unit)? = null,
        private val forwardedMessageListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null,
        private val imageClickListener: ((forwardedChatId: Long, messageId: Long) -> Unit)? = null
    ) {

        fun createView(viewType: Int): ContainerMessageView<*> {
            val messageView = when (viewType) {
                TYPE_MESSAGE_TEXT -> ChatTextMessageView(context).apply {
                    onMessageAuthorClickListener = onAuthorClickListener
                }
                TYPE_MESSAGE_IMAGE -> ChatImageTextMessageView(context).apply {
                    onMessageClickListener = messageClickListener
                }
                TYPE_MESSAGE_FORWARDED -> ChatForwardedView(context).apply {
                    setForwardedMessageClickListener(forwardedMessageListener)
                    setOnImageClickListener(imageClickListener)
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