package fileAccess

/**
 * TODO Document
 */
fun addPadding(
    byteArray : ByteArray,
    size      : Int
): ByteArray {
    val paddedByteArray = ByteArray(size)
    for (i in 0 until size) {
        if (i < byteArray.size) {
            paddedByteArray[i] = byteArray[i]
        }
        else {
            paddedByteArray[i] = 0x00
        }
    }
    return paddedByteArray
}