package mainMenu

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.AppNavType

/**
 * Displays main menu screen to navigate through the app.
 *
 * Options:
 * - Previous voting results
 * - Current voting process state
 * - Vote in current voting process
 *
 * @param colorPalette Colors used for the Material Theme.
 * @param navItemState Mutable value holder for used for navigation between screens.
 *
 */
@Composable
fun menuScreen(
    colorPalette : Colors,
    navItemState : MutableState<AppNavType>
) {
    val chainResultsB  = "Resultados de votaciones anteriores"
    val votingProcessB = "Ver estado actual de proceso de votación"
    val loginToVoteB   = "Log in"

    MaterialTheme(
        colors = colorPalette
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Button(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    onClick = {
                        navItemState.value = AppNavType.CHAIN
                    }
                ) {
                    Text(chainResultsB)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    onClick = {
                        navItemState.value = AppNavType.LIVE
                    }
                ) {
                    Text(votingProcessB)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    onClick = {
                        navItemState.value = AppNavType.LOGIN
                    }
                ) {
                    Text(loginToVoteB)
                }
            }
        }
    }
}