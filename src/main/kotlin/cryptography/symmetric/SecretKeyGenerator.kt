package cryptography.symmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * This class allows to hold a [SecretKey] object initialized outside the constructor,
 * requiring to call generate() first.
 *
 * @see SecretKey
 */

class SecretKeyGenerator {
    private val iterations    = 4096
    private lateinit var sKey : SecretKey

    /**
     * Returns the secret key.
     *
     * @see SecretKey
     */
    fun getSecretKey(): SecretKey {
        return sKey
    }

    /**
     * Generates a cryptographic secret key using the password based encryption (PBE) with 256-bit SHA and 256-bit AES
     * CBC algorithm within the Bouncy Castle Provider.
     *
     * @param password A [CharArray] corresponding to the password introduced by the user.
     * @param salt A [ByteArray] corresponding to the cryptographic salt.
     * @see SecretKeyFactory
     * @see PBEKeySpec
     */
    fun generate(
        password : CharArray,
        salt     : ByteArray
    ) {
        // Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val keyFact = SecretKeyFactory.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC", "BC")
        val pbeSpec = PBEKeySpec(password, salt, iterations)

        sKey = keyFact.generateSecret(pbeSpec)
    }
}









