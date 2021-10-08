package ru.kubov.feature_main_impl.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubov.core_ui.databinding.ItemChatInfoBinding
import ru.kubov.core_utils.domain.models.Chat

// TODO: 08.10.2021  add documentation
class SearchChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object TYPE {
        private const val TYPE_LOADER = 0
        private const val TYPE_CHAT = 1
    }

    private val userChats: List<Chat> = emptyList()
    private val recommendationsChats: List<Chat> = emptyList()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CHAT -> {
                val binding = ItemChatInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SearchChatViewHolder(binding)
            }
            else -> {
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
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

    class SearchChatViewHolder(val binding: ItemChatInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class LoaderViewHolder() : RecyclerView.ViewHolder() {

    }
}