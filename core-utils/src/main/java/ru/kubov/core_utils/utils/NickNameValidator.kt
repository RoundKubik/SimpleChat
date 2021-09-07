package ru.kubov.core_utils.utils

import ru.kubov.core_utils.error.NickNameValidatorResult
import java.util.regex.Pattern

/**
 * Class for validation input of nickname or another validation of nickname in current app
 */
class NickNameValidator {

    companion object {

        /**
         * Regular expression provides rules for allowed characters in nickname
         */
        const val ALLOWED_CHARACTERS_REGEX = "[a-z0-9_.-]*"

        private const val MIN_NICK_NAME_LEN = 4
        private const val MAX_NICK_NAME_LEN = 16
        private const val NOT_FIRST_CHARACTERS = "_.-"

        /**
         * Validation of nickname method
         *
         * If [nickname] length less than [MIN_NICK_NAME_LEN] return too short nickname error
         * [NickNameValidatorResult.TooShort].
         *
         * If [nickname] length bigger than [MAX_NICK_NAME_LEN] return too big nickname error
         * [NickNameValidatorResult.TooLong]
         *
         * If [nickname] begins with [NOT_FIRST_CHARACTERS], example: {_}, {.}, {-} return not valid first character
         * error [NickNameValidatorResult.NotValidFirstNickSymbol]
         *
         * If [nickname] contains not valid character: numbers, lowercase characters, special symbols({_}, {.}, {-})
         * return not valid symbol [NickNameValidatorResult.NotValidSymbol]
         *
         * In other cases returns unknown error [NickNameValidatorResult.Unknown]
         *
         * @return [NickNameValidatorResult]
         */
        fun validate(nickname: String): NickNameValidatorResult = when {
            nickname.length < MIN_NICK_NAME_LEN -> NickNameValidatorResult.TooShort
            nickname.length > MAX_NICK_NAME_LEN -> NickNameValidatorResult.TooLong
            NOT_FIRST_CHARACTERS.contains(nickname[0].lowercase()) -> NickNameValidatorResult.NotValidFirstNickSymbol
            !Pattern.matches(ALLOWED_CHARACTERS_REGEX, nickname) -> NickNameValidatorResult.NotValidSymbol
            else -> NickNameValidatorResult.Unknown
        }

    }
}