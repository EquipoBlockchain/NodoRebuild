package registry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import navigation.AppNavType
import registry.password.PasswordState
import registry.password.isPasswordValid
import registry.password.passwordInformation
import registry.passwordConfirmation.PasswordConfirmationState
import registry.passwordConfirmation.isPasswordConfirmed
import registry.passwordConfirmation.passwordConfirmationInformation

@Composable
fun registryScreen(
    colorPalette: Colors,
    navItemState: MutableState<AppNavType>
) {
    var user         by remember { mutableStateOf("") }
    var id           by remember { mutableStateOf("") }
    var password1    by remember { mutableStateOf("") }
    var password2    by remember { mutableStateOf("") }
    var passVisible1 by remember { mutableStateOf(false) }
    var passVisible2 by remember { mutableStateOf(false) }

    val passInfo      = remember { mutableStateOf("") }
    val passState     = remember { mutableStateOf(PasswordState.EMPTY) }
    val passConfState = remember { mutableStateOf(PasswordConfirmationState.EMPTY) }

    val eyeOutline    = painterResource("Icons/eye-outline.svg")
    val eyeOffOutline = painterResource("Icons/eye-off-outline.svg")
    val buttonText    = "Registrar"

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

                TextField(
                    value         = user,
                    onValueChange = { user = it },
                    label         = { Text("Usuario") },
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value         = id,
                    onValueChange = { id = it },
                    label         = { Text("Identificador") },
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value                = password1,
                    onValueChange        =
                        {
                            password1 = it
                            isPasswordValid(password1, passState, passInfo)
                        },
                    label                = { Text("Contraseña") },
                    singleLine           = true,
                    visualTransformation =
                        if (passVisible1){
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
                            if (passVisible1) {
                                eyeOutline
                            } else {
                                eyeOffOutline
                            }
                        IconButton(
                            onClick = {
                                passVisible1 = !passVisible1
                                if (passVisible2)
                                    passVisible2 = false
                            }
                        ) {
                            Image(painter = eyeIcon, "Visibility Indicator Password", alpha = 0.50F)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                passwordInformation(
                    colorPalette     = colorPalette,
                    passwordState    = passState.value,
                    errorDescription = passInfo.value
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value                = password2,
                    onValueChange        =
                        {
                            password2 = it
                            isPasswordConfirmed(password1, password2, passConfState)
                        },
                    label                = { Text("Confirmar contraseña") },
                    singleLine           = true,
                    visualTransformation =
                        if (passVisible2){
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
                            if (passVisible2) {
                                eyeOutline
                            } else {
                                eyeOffOutline
                            }
                        IconButton(
                            onClick = {
                                passVisible2 = !passVisible2
                                if (passVisible1)
                                    passVisible1 = false
                            }
                        ) {
                            Image(painter = eyeIcon, "Visibility Indicator Password Confirmation", alpha = 0.50F)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                passwordConfirmationInformation(
                    colorPalette              = colorPalette,
                    passwordConfirmationState = passConfState.value
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    contentPadding = PaddingValues(
                        start  = 20.dp,
                        top    = 12.dp,
                        end    = 20.dp,
                        bottom = 12.dp
                    ),
                    onClick = {
                        if (
                            passState.value     == PasswordState.VALID             &&
                            passConfState.value == PasswordConfirmationState.EQUAL
                        ) {
                            registryOutput(user, id, password1, password2)
                        }
                    }
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}

fun registryOutput(user: String, id: String, password1: String, password2: String) {
    println(user)
    println(id)
    println(password1)
    println(password2)
}