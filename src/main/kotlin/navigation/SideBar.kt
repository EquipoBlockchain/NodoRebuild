package navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays a sidebar with a button to return to the main menu.
 *
 * @param colorPalette Colors used for the Material Theme.
 *
 * @param navItemState Mutable value holder for used for navigation between screens.
 *
 */

@Composable
fun sideBarReturnToMenu(
    colorPalette: Colors,
    navItemState: MutableState<AppNavType>,
) {
    val back = "Regresar a menu"

    MaterialTheme(
        colors = colorPalette
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                onClick = {
                    navItemState.value = AppNavType.MENU
                }
            ) {
                Text(back)
            }
        }
    }
}