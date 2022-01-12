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