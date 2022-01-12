package registry.password

import androidx.compose.runtime.MutableState

fun isPasswordValid(
    password      : String,
    passwordState : MutableState<PasswordState>
) {
    if (password.length < 12) {
        passwordState.value = PasswordState.INVALID_SIZE
    }
    else if (!password.contains("[A-Z]".toRegex())) {
        passwordState.value = PasswordState.INVALID_NO_UPPERCASE
    }
    else if (!password.contains("[a-z]".toRegex())) {
        passwordState.value = PasswordState.INVALID_NO_LOWERCASE
    }
    else if (!password.contains("[0-9]".toRegex())) {
        passwordState.value = PasswordState.INVALID_NO_NUMBER
    }
    else {
        passwordState.value = PasswordState.VALID
    }
    // TODO Viability of https://github.com/nulab/zxcvbn4j integration
    // TODO is not similar to/does not contain the User/id
}