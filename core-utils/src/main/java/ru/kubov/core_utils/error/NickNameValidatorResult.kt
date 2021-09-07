package ru.kubov.core_utils.error

sealed class NickNameValidatorResult {
    object TooShort : NickNameValidatorResult()
    object TooLong : NickNameValidatorResult()
    object NotValidSymbol : NickNameValidatorResult()
    object NotValidFirstNickSymbol : NickNameValidatorResult()
    object Unknown: NickNameValidatorResult()
}