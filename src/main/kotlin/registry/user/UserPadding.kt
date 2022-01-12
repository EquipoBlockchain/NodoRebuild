package registry.user

fun addPadding(
    userByteArray: ByteArray
): ByteArray {
    val userPaddedByteArray = ByteArray(16)
    for (i in 0 until 16) {
        if (i < userByteArray.size) {
            userPaddedByteArray[i] = userByteArray[i]
        }
        else {
            userPaddedByteArray[i] = 0x00
        }
    }
    return userPaddedByteArray
}