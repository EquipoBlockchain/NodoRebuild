package cryptography.symmetric

import java.security.SecureRandom

/**
 * Generates an array of 16 bytes, using a [SecureRandom] object
 * to be used as cryptographic salt.
 *
 * @return [ByteArray] corresponding to a 16-byte salt.
 * @see SecureRandom
 */
fun getNewSalt(): ByteArray {
    val secureRandom = SecureRandom()
    val salt         = ByteArray(size = 16)

    secureRandom.nextBytes(salt)

    return salt
}