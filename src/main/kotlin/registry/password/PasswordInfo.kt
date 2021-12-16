package registry.password

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun passwordInformation(colorPalette: Colors, passwordState: PasswordState, errorDescription: String) {
    Crossfade(targetState = passwordState) { passwordState ->
        when (passwordState) {
            PasswordState.EMPTY -> {
                Text(
                    text = "",
                    color = Color.Transparent
                )
            }
            PasswordState.VALID -> {
                Text(
                    text  = "Contraseña Valida",
                    color = Color.Green
                )
            }
            PasswordState.INVALID -> {
                Text(
                    text  = "Error: $errorDescription",
                    color = colorPalette.error
                )
            }
        }
    }
}