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

package registry.user

import androidx.compose.runtime.MutableState

fun isUserValid(
    user      : String,
    userState : MutableState<UserState>
) {
    val userSize = user.toByteArray(Charsets.UTF_8).size

    if (
        (userSize < 6)  or
        (userSize > 16)
    ) {
        userState.value = UserState.INVALID_SIZE
    } else if (user.contains("[\\W]".toRegex())) {
        userState.value = UserState.INVALID_CHAR
    } else {
        userState.value = UserState.VALID
    }

}