package registry.passwordConfirmation

import androidx.compose.runtime.MutableState

fun isPasswordConfirmed(password1: String, password2: String, passwordState: MutableState<PasswordConfirmationState>) {
    if (password1 == password2) {
        passwordState.value = PasswordConfirmationState.EQUAL
    }  else {
        passwordState.value = PasswordConfirmationState.NOT_EQUAL
    }
}