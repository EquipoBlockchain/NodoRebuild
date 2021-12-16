package registry.password

import androidx.compose.runtime.MutableState

fun isPasswordValid(password: String, passwordState: MutableState<PasswordState>, errorDescription: MutableState<String>) {
    if (password.length < 8) {
        passwordState.value    = PasswordState.INVALID
        errorDescription.value = "La contraseña debe contener almenos 8 caracteres"
    } else if (!password.contains(".*[A-Z].*".toRegex())) {
        passwordState.value    = PasswordState.INVALID
        errorDescription.value = "La contraseña debe contener almenos 1 mayúscula"
    } else if (!password.contains(".*[a-z].*".toRegex())) {
        passwordState.value    = PasswordState.INVALID
        errorDescription.value = "La contraseña debe contener almenos 1 minúscula"
    } else if (!password.contains(".*[0-9].*".toRegex())) {
        passwordState.value    = PasswordState.INVALID
        errorDescription.value = "La contraseña debe contener almenos 1 número"
    }else {
        passwordState.value    = PasswordState.VALID
        errorDescription.value = "No hay error"
    }
}