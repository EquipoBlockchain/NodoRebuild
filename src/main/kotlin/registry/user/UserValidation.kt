package registry.user

import androidx.compose.runtime.MutableState

fun isUserSizeValid(user: String, userState: MutableState<UserState>) {
    if (user.toByteArray(Charsets.UTF_8).size > 16) {
        userState.value        = UserState.INVALID_SIZE
    } else {
        userState.value        = UserState.VALID_SIZE
    }
}