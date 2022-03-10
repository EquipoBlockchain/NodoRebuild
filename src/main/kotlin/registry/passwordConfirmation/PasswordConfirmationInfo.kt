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

package registry.passwordConfirmation

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun passwordConfirmationInformation(
    colorPalette              : Colors,
    passwordConfirmationState : PasswordConfirmationState
) {
    Crossfade(targetState = passwordConfirmationState) {
        when (it) {
            PasswordConfirmationState.EMPTY -> {
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
            PasswordConfirmationState.EQUAL -> {
                Text(
                    text  = "Las contraseñas coinciden",
                    color = Color.Green
                )
            }
            PasswordConfirmationState.NOT_EQUAL -> {
                Text(
                    text  = "Error: Las contraseñas NO coinciden",
                    color = colorPalette.error
                )
            }
        }
    }
}