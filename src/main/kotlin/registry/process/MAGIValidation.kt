package registry.process

import fileAccess.deleteEVAFile
import registry.user.addPadding

fun isProcessValid(
    path                : String,
    fileName            : String,
    format              : String,
    userPaddedByteArray : ByteArray,
    idByteArray         : ByteArray
): Boolean {
    // TODO Verification with each MAGI system
    // Placeholder user : JulioRegalado
    // Placeholder id   : 12345678

    if (
        // TODO Use response from MAGI
        userPaddedByteArray.contentEquals(addPadding("JulioRegalado".toByteArray(Charsets.UTF_8))) &&
        idByteArray.contentEquals("12345678".toByteArray(Charsets.UTF_8))
    ) {
        return true
    }

    deleteEVAFile(
        path     = path,
        fileName = fileName,
        format   = format
    )

    return false
}