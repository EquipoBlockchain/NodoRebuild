package registry.identifier

import androidx.compose.runtime.MutableState

fun isIdValid(
    id      : String,
    idState : MutableState<IdentifierState>
) {
    val idSize = id.toByteArray(Charsets.UTF_8).size

    if (idSize != 8) {
        idState.value = IdentifierState.INVALID_SIZE
    }
    else if (
        id.contains("[\\W]".toRegex()) or
        id.contains('_')
    ) {
        idState.value = IdentifierState.INVALID_CHAR
    }
    else {
        idState.value = IdentifierState.VALID
    }

}