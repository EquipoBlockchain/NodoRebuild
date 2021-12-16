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

@Composable
fun menuScreen(
    colorPalette: Colors,
    navItemState: MutableState<AppNavType>
) {
    val chainResultsB  by remember { mutableStateOf("Resultados de votaciones anteriores") }
    val votingProcessB by remember { mutableStateOf("Ver estado actual de proceso de votación") }
    val loginToVoteB   by remember { mutableStateOf("Votar") }

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