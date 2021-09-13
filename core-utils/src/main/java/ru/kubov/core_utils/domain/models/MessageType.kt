package ru.kubov.core_utils.domain.models

// TODO: 11.09.2021  
sealed class MessageType {
    object Unsupported : MessageType()
    object Text : MessageType()
    object SingleImage : MessageType()
    object Forward : MessageType()
}
