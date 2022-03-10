/**
 * Copyright 2021 Kyle Elbjorn
 *
 * This file is part of GEHIRN Node.
 *
 * GEHIRN Node is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * GEHIRN Node is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with GEHIRN Node.
 * If not, see <https://www.gnu.org/licenses/>.
 */

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