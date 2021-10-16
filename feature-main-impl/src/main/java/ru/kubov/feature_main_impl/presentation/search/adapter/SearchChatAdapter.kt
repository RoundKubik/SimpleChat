package ru.kubov.feature_main_impl.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubov.core_ui.databinding.ItemSearchLottieLoaderBinding
import com.kubov.core_ui.presentation.view.chat.SearchChatView
import ru.kubov.core_utils.domain.models.Chat

// TODO: 08.10.2021  add documentation
class SearchChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_LOADER = 0
        private const val TYPE_CHAT = 1
        private const val USER_CHATS_AREA_POSITION = 0
    }

    private var userChats: List<Chat> = emptyList()
    private var recommendationsChats: List<Chat> = emptyList()

    private var userChatsArea: SearchChatsArea? = null
    private var recommendationsChatsArea: SearchChatsArea? = null

    private var loaderVisible: Boolean = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            if (field) {
                notifyItemInserted(itemCount - 1)
            } else {
                notifyItemRemoved(itemCount)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_CHAT -> {
            val view = SearchChatView(parent.context)
            SearchChatViewHolder(view)
        }
        else -> {
            val binding = ItemSearchLottieLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoaderViewHolder(binding)
        }
    }

    // TODO: 08.10.2021 add logic to get chat from sections of user chats and recommendations
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchChatViewHolder) {
            val chat = getChat(position)
            val area = getAreaByPosition(position)
            holder.bindChat(chat, area?.toString(holder.view.context))
        }
    }

    override fun getItemCount(): Int = userChats.size + recommendationsChats.size + if (loaderVisible) {
        1
    } else {
        0
    }

    override fun getItemViewType(position: Int): Int = if (loaderVisible && position == itemCount - 1) {
        TYPE_LOADER
    } else {
        TYPE_CHAT
    }

    // TODO: 08.10.2021 add documentation
    class SearchChatViewHolder(val view: SearchChatView) : RecyclerView.ViewHolder(view) {

        fun bindChat(chat: Chat, header: String?) {
            view.showChatContent(chat, header)
        }

    }

    // TODO: 08.10.2021 add documentation
    class LoaderViewHolder(val binding: ItemSearchLottieLoaderBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemSearchLottieLoaderLavLoader.cancelAnimation()
        }

        fun pause() {
            binding.itemSearchLottieLoaderLavLoader.pauseAnimation()
        }

        fun play() {
            binding.itemSearchLottieLoaderLavLoader.playAnimation()
        }
    }

    // TODO: 08.10.2021 add documentation
    fun setChatsWithSections(
        userChats: List<Chat>,
        userChatsArea: SearchChatsArea?,
        recommendationsChats: List<Chat>,
        recommendationsChatsArea: SearchChatsArea
    ) {
        this.userChats = userChats
        this.userChatsArea = userChatsArea
        this.recommendationsChats = recommendationsChats
        this.recommendationsChatsArea = recommendationsChatsArea
        notifyDataSetChanged()
    }

    private fun getChat(position: Int) = if (position < userChats.size) {
        userChats[position]
    } else {
        recommendationsChats[position - userChats.size]
    }

    private fun getAreaByPosition(position: Int): SearchChatsArea? {
        return if (userChats.isNotEmpty() && position == USER_CHATS_AREA_POSITION) {
            userChatsArea
        } else if (recommendationsChats.isNotEmpty() && position == userChats.size) {
            recommendationsChatsArea
        } else {
            null
        }
    }
}