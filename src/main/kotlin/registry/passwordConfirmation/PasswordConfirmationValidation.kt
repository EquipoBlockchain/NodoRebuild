package registry.passwordConfirmation

import androidx.compose.runtime.MutableState

fun isPasswordConfirmed(
    password1         : String,
    password2         : String,
    passwordConfState : MutableState<PasswordConfirmationState>
) {
    if (password1 == password2) {
        passwordConfState.value = PasswordConfirmationState.EQUAL
    }
    else {
        passwordConfState.value = PasswordConfirmationState.NOT_EQUAL
    }
}