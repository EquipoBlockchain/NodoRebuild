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

package registry.password

import androidx.compose.runtime.MutableState

fun isPasswordValid(
    password      : String,
    passwordState : MutableState<PasswordState>
) {
    if (password.length < 12) {
        passwordState.value = PasswordState.INVALID_SIZE
    } else if (!password.contains("[A-Z]".toRegex())) {
        passwordState.value = PasswordState.INVALID_NO_UPPERCASE
    } else if (!password.contains("[a-z]".toRegex())) {
        passwordState.value = PasswordState.INVALID_NO_LOWERCASE
    } else if (!password.contains("[0-9]".toRegex())) {
        passwordState.value = PasswordState.INVALID_NO_NUMBER
    } else {
        passwordState.value = PasswordState.VALID
    }
    // TODO Viability of https://github.com/nulab/zxcvbn4j integration
    // TODO is not similar to/does not contain the User/id
}