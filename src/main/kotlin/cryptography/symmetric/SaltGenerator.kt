package cryptography.symmetric

fun getNewSalt(): ByteArray {
    //TODO SecureRandom 16-byte salt generator
    val provisionalSalt = byteArrayOf(
        0x7D.toByte(),
        0x60.toByte(),
        0x43.toByte(),
        0x5F.toByte(),
        0x02.toByte(),
        0xE9.toByte(),
        0xE0.toByte(),
        0xAE.toByte(),
        0x7D.toByte(),
        0x60.toByte(),
        0x43.toByte(),
        0x5F.toByte(),
        0x02.toByte(),
        0xE9.toByte(),
        0xE0.toByte(),
        0xAE.toByte()
    )
    return provisionalSalt
}