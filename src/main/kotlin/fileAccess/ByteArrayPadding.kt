package fileAccess

/**
 * Fills the empty spaces in a [ByteArray] with 0x00 bytes, until the size determined is reached.
 *
 * @param byteArray Original byte array.
 * @param size Desired final array size.
 * @return [ByteArray] Padded byte array.
 */
fun addPadding(
    byteArray : ByteArray,
    size      : Int
): ByteArray {
    val paddedByteArray = ByteArray(size)
    for (i in 0 until size) {
        if (i < byteArray.size) {
            paddedByteArray[i] = byteArray[i]
        } else {
            paddedByteArray[i] = 0x00
        }
    }
    return paddedByteArray
}