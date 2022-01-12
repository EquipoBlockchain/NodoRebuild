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
import registry.identifier.IdentifierState
import registry.identifier.idInformation
import registry.password.PasswordState
import registry.password.passwordInformation
import registry.passwordConfirmation.PasswordConfirmationState
import registry.passwordConfirmation.passwordConfirmationInformation
import registry.user.UserState
import registry.user.userInformation

/**
 * Displays registry screen and captures the user's input.
 *
 * Data captured:
 * - User
 * - Identification code
 * - Password
 * - Password for confirmation
 *
 * @param colorPalette Colors used for the Material Theme.
 * @param navItemState Mutable value holder for used for navigation between screens.
 *
 */
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

    val userState     = remember { mutableStateOf(UserState.EMPTY) }
    val idState       = remember { mutableStateOf(IdentifierState.EMPTY) }
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
                    onValueChange = {
                        user = it
                        isInfoValid(
                            user              = user,
                            id                = id,
                            password1         = password1,
                            password2         = password2,
                            idState           = idState,
                            userState         = userState,
                            passwordState     = passState,
                            passwordConfState = passConfState
                        )
                    },
                    label         = { Text("Usuario") },
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                userInformation(
                    colorPalette = colorPalette,
                    userState    = userState.value
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value         = id,
                    onValueChange = {
                        id = it
                        isInfoValid(
                            user              = user,
                            id                = id,
                            password1         = password1,
                            password2         = password2,
                            idState           = idState,
                            userState         = userState,
                            passwordState     = passState,
                            passwordConfState = passConfState
                        )
                    },
                    label         = { Text("Identificador") },
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                idInformation(
                    colorPalette = colorPalette,
                    idState      = idState.value
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value                = password1,
                    onValueChange        = {
                        password1 = it
                        isInfoValid(
                            user              = user,
                            id                = id,
                            password1         = password1,
                            password2         = password2,
                            idState           = idState,
                            userState         = userState,
                            passwordState     = passState,
                            passwordConfState = passConfState
                        )
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
                            Image(
                                painter            = eyeIcon,
                                contentDescription = "Visibility Indicator Password",
                                alpha              = 0.50F
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                passwordInformation(
                    colorPalette     = colorPalette,
                    passwordState    = passState.value
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value                = password2,
                    onValueChange        = {
                        password2 = it
                        isInfoValid(
                            user              = user,
                            id                = id,
                            password1         = password1,
                            password2         = password2,
                            idState           = idState,
                            userState         = userState,
                            passwordState     = passState,
                            passwordConfState = passConfState
                        )
                    },
                    label                = { Text("Confirmar contraseña") },
                    singleLine           = true,
                    visualTransformation =
                        if (passVisible2) {
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
                            Image(
                                painter            = eyeIcon,
                                contentDescription = "Visibility Indicator Password Confirmation",
                                alpha              = 0.50F
                            )
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
                        isInfoValid(
                            user              = user,
                            id                = id,
                            password1         = password1,
                            password2         = password2,
                            idState           = idState,
                            userState         = userState,
                            passwordState     = passState,
                            passwordConfState = passConfState
                        )
                        if (
                            userState.value     == UserState.VALID                 &&
                            idState.value       == IdentifierState.VALID           &&
                            passState.value     == PasswordState.VALID             &&
                            passConfState.value == PasswordConfirmationState.EQUAL
                        ) {
                            registryProcess(
                                user     = user,
                                id       = id,
                                password = password1
                            )
                        }
                    }
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}
