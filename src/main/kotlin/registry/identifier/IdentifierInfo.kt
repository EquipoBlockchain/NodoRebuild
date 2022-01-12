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