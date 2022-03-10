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

package login.voting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import login.votingSessionVerifiedSignature

/**
 * TODO Document
 *
 */
@Composable
fun votingScreen(
    colorPalette: Colors
) {
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
                Text(pattern)
                //TODO bit activation
                if(pattern != "Unstable REI Pattern") {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = blockNumber.toString(),
                        color = colorPalette.onBackground
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = description,
                        color = colorPalette.onBackground
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = question,
                        color = colorPalette.onBackground
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        options.forEach { option ->
                            Column {
                                Text(
                                    text = "Option #${option.optionNumber}: ",
                                    color = colorPalette.onBackground
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    contentPadding = PaddingValues(
                                        start  = 20.dp,
                                        top    = 12.dp,
                                        end    = 20.dp,
                                        bottom = 12.dp
                                    ),
                                    onClick = {
                                        votingSessionVerifiedSignature(
                                            blockNumber              = blockNumber,
                                            selectedOptionNumber     = option.optionNumber,
                                            selectedOptionDefinition = option.optionDefinition
                                        )
                                    }
                                ) {
                                    Text(text = option.optionDefinition)
                                }

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            Spacer(modifier = Modifier.width(40.dp))
                        }
                    }
                }
            }
        }
    }
}
