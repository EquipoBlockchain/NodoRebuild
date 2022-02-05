package registry.validations

import fileAccess.addPadding

fun isProcessValid(
    userPaddedByteArray : ByteArray,
    idByteArray         : ByteArray
): Boolean {
    // TODO Verification with each MAGI system
    // Placeholder user : JulioRegalado
    // Placeholder id   : 12345678

    if (
        // TODO Use response from MAGI
        userPaddedByteArray.contentEquals(addPadding("JulioRegalado".toByteArray(Charsets.UTF_8), 16)) &&
        idByteArray.contentEquals("12345678".toByteArray(Charsets.UTF_8))
    ) {
        return true
    }

    return false
}