package ru.kubov.core_utils.domain.models

/**
 * Class keeps info about message loading status into device
 */
sealed class MessageStatus {
    object Ready : MessageStatus()
    object ContentUploading: MessageStatus()
    object Uploading : MessageStatus()
    object Failed : MessageStatus()
}