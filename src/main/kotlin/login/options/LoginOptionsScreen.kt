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

package login.options

import androidx.compose.foundation.background
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
import login.voting.votingSession
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
                .fillMaxHeight()
                .background(colorPalette.background),
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
                            //TODO loading screen
                            if(votingSession()) {
                                navItemState.value = AppNavType.LOGIN_VOTING
                            } else {
                                TODO() //voting error screen & retry
                            }
                        }
                    ){
                        Text(voteButtonText)
                    }
                }
            }
        }
    }
}