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

package registry.identifier

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun idInformation(
    colorPalette : Colors,
    idState      : IdentifierState
) {
    Crossfade(targetState = idState) {
        when (it) {
            IdentifierState.EMPTY -> {
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
            IdentifierState.VALID -> {
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
            IdentifierState.INVALID_SIZE -> {
                Text(
                    text  = "Error: Tamaño invalido (El identificador debe poseer 8 caracteres).",
                    color = colorPalette.error
                )
            }
            IdentifierState.INVALID_CHAR -> {
                Text(
                    text  = "Error: El usuario ingresado contiene un caracter no permitido.",
                    color = colorPalette.error
                )
            }
        }
    }
}