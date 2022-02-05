package login.options

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import login.verificationSession
import login.votingSession
import navigation.AppNavType

/**
 * Displays log in options screen.
 *
 * @param colorPalette Colors used for the Material Theme.
 * @param navItemState Mutable value holder for used for navigation between screens.
 *
 */
@Composable
fun loginOptionsScreen(
    colorPalette : Colors,
    navItemState : MutableState<AppNavType>
) {
    val verifyButtonText = "Verificar votaciones anteriores"
    val voteButtonText   = "Participar en la votación actual"

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
                Row {
                    Button(
                        contentPadding = PaddingValues(
                            start  = 20.dp,
                            top    = 12.dp,
                            end    = 20.dp,
                            bottom = 12.dp
                        ),
                        onClick = {
                            navItemState.value = AppNavType.LOGIN_VERIFICATIONS
                            verificationSession()
                        }
                    ){
                        Text(verifyButtonText)
                    }

                    Spacer(modifier = Modifier.width(40.dp))

                    Button(
                        contentPadding = PaddingValues(
                            start  = 20.dp,
                            top    = 12.dp,
                            end    = 20.dp,
                            bottom = 12.dp
                        ),
                        onClick = {
                            navItemState.value = AppNavType.LOGIN_VOTING
                            votingSession()
                        }
                    ){
                        Text(voteButtonText)
                    }
                }
            }
        }
    }
}