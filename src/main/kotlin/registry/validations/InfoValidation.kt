package registry.validations

import androidx.compose.runtime.MutableState
import registry.identifier.IdentifierState
import registry.identifier.isIdValid
import registry.password.PasswordState
import registry.password.isPasswordValid
import registry.passwordConfirmation.PasswordConfirmationState
import registry.passwordConfirmation.isPasswordConfirmed
import registry.user.UserState
import registry.user.isUserValid

fun isInfoValid(
    user              : String,
    id                : String,
    password1         : String,
    password2         : String,
    userState         : MutableState<UserState>,
    idState           : MutableState<IdentifierState>,
    passwordState     : MutableState<PasswordState>,
    passwordConfState : MutableState<PasswordConfirmationState>
) {
    isUserValid(
        user      = user,
        userState = userState
    )

    isIdValid(
        id      = id,
        idState = idState
    )

    isPasswordValid(
        password      = password1,
        passwordState = passwordState
    )

    isPasswordConfirmed(
        password1         = password1,
        password2         = password2,
        passwordConfState = passwordConfState
    )
}