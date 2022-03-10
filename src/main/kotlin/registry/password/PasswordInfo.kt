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

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun passwordInformation(
    colorPalette     : Colors,
    passwordState    : PasswordState
) {
    Crossfade(targetState = passwordState) {
        when (it) {
            PasswordState.EMPTY -> {
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
            PasswordState.VALID -> {
                Text(
                    text  = "Contraseña Valida",
                    color = Color.Green
                )
            }
            PasswordState.INVALID_SIZE -> {
                Text(
                    text  = "Error: La contraseña debe contener almenos 12 caracteres",
                    color = colorPalette.error
                )
            }
            PasswordState.INVALID_NO_UPPERCASE -> {
                Text(
                    text  = "Error: La contraseña debe contener almenos 1 mayúscula",
                    color = colorPalette.error
                )
            }
            PasswordState.INVALID_NO_LOWERCASE -> {
                Text(
                    text  = "Error: La contraseña debe contener almenos 1 minúscula",
                    color = colorPalette.error
                )
            }
            PasswordState.INVALID_NO_NUMBER -> {
                Text(
                    text  = "Error: La contraseña debe contener almenos 1 número",
                    color = colorPalette.error
                )
            }
        }
    }
}