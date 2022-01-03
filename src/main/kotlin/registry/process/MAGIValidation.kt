package registry.process

fun isProcessValid(
    userByteArray : ByteArray,
    idByteArray   : ByteArray
): Boolean {
    // TODO Verification with each MAGI system
    // Placeholder user : JulioRegalado123
    // Placeholder id   : 12345678

    if (
        userByteArray.contentEquals("JulioRegalado123".toByteArray(Charsets.UTF_8)) &&
        idByteArray.contentEquals("12345678".toByteArray(Charsets.UTF_8))
    ) {
        return true
    }

    return false
}