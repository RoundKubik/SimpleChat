package ru.kubov.core_utils.domain.models

/**
 * Class provides info about type of message
 */
sealed class MessageType {
    object Text : MessageType()
    object SingleImage : MessageType()
}
