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