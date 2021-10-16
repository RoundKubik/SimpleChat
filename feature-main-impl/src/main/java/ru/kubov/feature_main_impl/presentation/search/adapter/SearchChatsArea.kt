package ru.kubov.feature_main_impl.presentation.search.adapter

import android.content.Context
import ru.kubov.feature_main_impl.R

// TODO: 08.10.2021  add documentation
sealed class SearchChatsArea {

    object UserChats : SearchChatsArea()
    object RecommendationChats: SearchChatsArea()

    // TODO: 08.10.2021 add documentation 
    fun toString(context: Context) = when(this) {
        UserChats -> context.getString(R.string.user_chats)
        RecommendationChats -> context.getString(R.string.recommendations_chats)
    }

}