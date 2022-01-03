package registry.user

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun userInformation(colorPalette: Colors, userState: UserState) {
    Crossfade(targetState = userState) {
        when (it) {
            UserState.EMPTY -> {
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
            UserState.VALID_SIZE -> {
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
            UserState.INVALID_SIZE -> {
                Text(
                    text  = "Error: Tamaño invalido.",
                    color = colorPalette.error
                )
            }
        }
    }
}