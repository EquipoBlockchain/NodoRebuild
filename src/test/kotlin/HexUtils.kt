private val digits = "0123456789ABCDEF".toCharArray()

fun toHex(bytes: ByteArray): String {
    val hexChars = CharArray(bytes.size * 2)
    for (j in bytes.indices) {
        val v = bytes[j].toInt() and 0xFF

        hexChars[j * 2] = digits[v ushr 4]
        hexChars[j * 2 + 1] = digits[v and 0x0F]
    }
    return String(hexChars)
}