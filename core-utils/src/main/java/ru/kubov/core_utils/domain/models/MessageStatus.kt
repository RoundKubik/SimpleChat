package ru.kubov.core_utils.domain.models

// TODO: 11.09.2021  
sealed class MessageStatus {
    object Ready : MessageStatus()
    object ContentUploading: MessageStatus()
    object Uploading : MessageStatus()
    object Failed : MessageStatus()


    // TODO: 13.09.2021 remove convert methods 
    fun toInt(): Int = when (this) {
        Ready -> 0
        ContentUploading -> 1
        Uploading -> 2
        Failed -> 3
    }

    companion object {

        fun fromInt(int: Int): MessageStatus? = when (int) {
            0 -> Ready
            1 -> ContentUploading
            2 -> Uploading
            3 -> Failed
            else -> null
        }

    }
}