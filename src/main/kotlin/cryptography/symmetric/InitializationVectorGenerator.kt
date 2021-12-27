package cryptography.symmetric

import java.security.SecureRandom

/**
 * Generates an array of 16 bytes, using a [SecureRandom] object
 * to be used as a cryptographic initialization vector.
 *
 * @return [ByteArray] corresponding to a 16-byte initialization vector.
 * @see SecureRandom
 */

fun getNewInitVectorBytes(): ByteArray {
    val secureRandom    = SecureRandom()
    val initVectorBytes = ByteArray(size = 16)

    secureRandom.nextBytes(initVectorBytes)

    return initVectorBytes
}