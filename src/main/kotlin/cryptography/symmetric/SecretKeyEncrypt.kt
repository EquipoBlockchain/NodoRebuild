package cryptography.symmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * This class allows to encrypt a given input, obtaining a ciphered [ByteArray] and its calculated length as an [Integer].
 *
 * - [cipherBytes] Used to store the byte array resulting from the encryption.
 * - [cipherBytesLength] Used to store the calculated length.
 *
 * @see Cipher
 */
class SecretKeyEncrypt {
    lateinit var cipherBytes : ByteArray
    var cipherBytesLength    = 0

    /**
     * Encrypts a provided byte array with the AES CBC algorithm and a PKCS #7 padding utilizing the Bouncy Castle
     * Provider.
     *
     * @param input The [ByteArray] to be encrypted.
     * @param secretKey The [SecretKey] used for encryption.
     * @param initVectorBytes The initialization bytes to be used in the encryption.
     * @see Cipher
     * @see IvParameterSpec
     */
    fun encrypt(
        input           : ByteArray,
        secretKey       : SecretKey,
        initVectorBytes : ByteArray
    ) {
        //Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val cipher         = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")
        val initVectorSpec = IvParameterSpec(initVectorBytes)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, initVectorSpec)

        cipherBytes       =  ByteArray(cipher.getOutputSize(input.size))
        cipherBytesLength =  cipher.update(input, 0, input.size, cipherBytes, 0)
        cipherBytesLength += cipher.doFinal(cipherBytes, cipherBytesLength)
    }
}