fun main() {
    val i = "0640"
    val bytes = stringToByteArray(i)
    println(toHex(bytes))
    println(byteArrayToInt(bytes))
}

fun stringToByteArray(
    string: String
): ByteArray {
    val bytes = ByteArray(string.length)
    val chars = string.toCharArray()
    for (i in chars.indices) {
        bytes[i] = chars[i].code.toByte()
    }
    return bytes
}

fun byteArrayToInt(
    bytes: ByteArray
): Int {
    val chars = CharArray(bytes.size)
    for (i in bytes.indices) {
        chars[i] = bytes[i].toInt().toChar()
    }
    return chars.concatToString().toInt()
}