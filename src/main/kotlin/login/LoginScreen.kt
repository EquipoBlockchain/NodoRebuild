package login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
 *
 * @param navItemState Mutable value holder for used for navigation between screens.
 *
 */

@Composable
fun loginScreen(
    colorPalette: Colors,
    navItemState: MutableState<AppNavType>
) {
    var id          by remember { mutableStateOf("") }
    var password    by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }

    val buttonText    = "Log in"
    val regButtonText = "Registrar"
    val eyeOutline    = painterResource("Icons/eye-outline.svg")
    val eyeOffOutline = painterResource("Icons/eye-off-outline.svg")
    val image         = painterResource("Icons/temp-icon.png")

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

                Image(
                    painter            = image,
                    contentDescription = "Profile picture",
                    modifier           = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(3.dp, color = MaterialTheme.colors.secondary, CircleShape)
                )

                OutlinedTextField(
                    value         = id,
                    onValueChange = { id = it },
                    label         = { Text("Identificador") },
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value                = password,
                    onValueChange        = { password = it },
                    label                = { Text("Contraseña") },
                    singleLine           = true,
                    visualTransformation =
                    if (passVisible){
                        VisualTransformation.None
                    } else {
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
                            } else {
                                eyeOffOutline
                            }
                        IconButton(
                            onClick = {
                                passVisible = !passVisible
                            }
                        ) {
                            Image(painter = eyeIcon, "Visibility Indicator", alpha = 0.50F)
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
                        loginOutput(id, password)
                    }
                ) {
                    Text(buttonText)
                }

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

fun loginOutput(id: String, password: String) {
    println(id)
    println(password)
}