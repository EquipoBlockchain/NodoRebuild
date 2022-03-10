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

package login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import navigation.AppNavType

/**
 * Displays log in screen and captures the user's input.
 *
 * Data captured:
 * - User
 * - Password
 *
 * @param colorPalette Colors used for the Material Theme.
 * @param navItemState Mutable value holder for used for navigation between screens.
 *
 */
@Composable
fun loginScreen(
    colorPalette : Colors,
    navItemState : MutableState<AppNavType>
) {
    val logButtonText = "Log in"
    val regButtonText = "Registrar"

    val eyeOutline    = painterResource("Icons/eye-outline.svg")
    val eyeOffOutline = painterResource("Icons/eye-off-outline.svg")
    val image         = painterResource("Icons/temp-icon.png")

    var user        by remember { mutableStateOf("") }
    var password    by remember { mutableStateOf("") }

    var passVisible by remember { mutableStateOf(false) }

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

                Image(
                    painter            = image,
                    contentDescription = "Profile picture",
                    modifier           = Modifier
                        .size(
                            size = 90.dp
                        )
                        .clip(
                            shape = CircleShape
                        )
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colors.secondary,
                            shape = CircleShape
                        )
                )

                OutlinedTextField(
                    value         = user,
                    onValueChange = { user = it },
                    label         = { Text(text = "Usuario") },
                    colors        = TextFieldDefaults.textFieldColors(
                        textColor       = colorPalette.onBackground,
                        backgroundColor = colorPalette.background
                    ),
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value                = password,
                    onValueChange        = { password = it },
                    label                = { Text("Contraseña") },
                    colors        = TextFieldDefaults.textFieldColors(
                        textColor       = colorPalette.onBackground,
                        backgroundColor = colorPalette.background
                    ),
                    singleLine           = true,
                    visualTransformation =
                    if (passVisible){
                        VisualTransformation.None
                    }
                    else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions      = KeyboardOptions(
                        keyboardType   = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect    = false
                    ),
                    trailingIcon         = {
                        val eyeIcon: Painter =
                            if (passVisible) {
                                eyeOutline
                            }
                            else {
                                eyeOffOutline
                            }
                        IconButton(
                            onClick = {
                                passVisible = !passVisible
                            }
                        ) {
                            Image(
                                painter = eyeIcon,
                                contentDescription = "Visibility Indicator",
                                colorFilter = tint(color = colorPalette.onBackground),
                                alpha = 0.50F
                            )
                        }
                    }
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
                        loginProcess(
                            user         = user,
                            password     = password,
                            navItemState = navItemState
                        )
                    }
                ) {
                    Text(logButtonText)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    contentPadding = PaddingValues(
                        start  = 20.dp,
                        top    = 12.dp,
                        end    = 20.dp,
                        bottom = 12.dp
                    ),
                    onClick = {
                        navItemState.value = AppNavType.REGISTRY
                    }
                ){
                    Text(regButtonText)
                }
            }
        }
    }
}