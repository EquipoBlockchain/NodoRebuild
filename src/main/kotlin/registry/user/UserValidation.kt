package registry.user

import androidx.compose.runtime.MutableState

fun isUserValid(
    user      : String,
    userState : MutableState<UserState>
) {
    val userSize = user.toByteArray(Charsets.UTF_8).size

    if (
        (userSize < 6)  or
        (userSize > 16)
    ) {
        userState.value = UserState.INVALID_SIZE
    } else if (user.contains("[\\W]".toRegex())) {
        userState.value = UserState.INVALID_CHAR
    } else {
        userState.value = UserState.VALID
    }

}