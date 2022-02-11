package fileAccess.fileTypeEVA

import cryptography.symmetric.SecretKeyDecrypt
import cryptography.symmetric.SecretKeyGenerator
import mu.KotlinLogging
import java.io.File
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

private val logger = KotlinLogging.logger {}

/**
 * This class allows allocating three [ByteArray] objects initialized outside the constructor, requiring to call
 * [synchronizeEVAKeyPair] first.
 *
 * - [idFromEVA] is the ID obtained from the file.
 * - [publicKeyX509Encoded] is the Public Key encoded according to the X.509 standard, obtained by decrypting the file.
 * - [privateKeyPKCS8Encoded] is the Private Key encoded according to the PKCS #8 standard, obtained by decrypting the
 * file.
 *
 */
class FileEVAKeyPairSync {
    private lateinit var idFromEVA              : ByteArray
    private lateinit var publicKeyX509Encoded   : ByteArray
    private lateinit var privateKeyPKCS8Encoded : ByteArray

    /**
     * Returns the ID retrieved from the EVA file.
     *
     * @return [String]
     */
    fun getIDString(): String {
        return idFromEVA.toString(Charsets.UTF_8)
    }

    /**
     * Returns the public key encoded according to the X.509 standard as a byte array.
     *
     * @return [ByteArray]
     * @see X509EncodedKeySpec
     */
    fun getPublicKeyX509Encoded(): ByteArray {
        return publicKeyX509Encoded
    }

    /**
     * Returns the private key encoded according to the PKCS #8 standard as a byte array.
     *
     * @return [ByteArray]
     * @see PKCS8EncodedKeySpec
     */
    fun getPrivateKeyPKCS8Encoded(): ByteArray {
        return privateKeyPKCS8Encoded
    }

    /**
     * Calls the necessary functions for decrypting a file, obtaining the respective public key and private key.
     *
     * KotlinLogging Implemented.
     *
     * @param file File to be decrypted.
     * @param password Password to be used during decryption.
     * @param userPadded Username to be compared with header information.
     * @return [Boolean] Returns true if the keys where retrieved correctly. Returns false otherwise.
     */
    fun synchronizeEVAKeyPair(
        file       : File,
        password   : CharArray,
        userPadded : ByteArray
    ): Boolean {
        val fileEVAReaderInstance = FileEVAReader()
        fileEVAReaderInstance.readEVA(
            file = file
        )

        val userPaddedByteArrayFromEVA          = fileEVAReaderInstance.userPadded
        idFromEVA                               = fileEVAReaderInstance.id
        val saltFromEVA                         = fileEVAReaderInstance.salt
        val initVectorBytesFromEVA              = fileEVAReaderInstance.initVectorBytes
        val cipherPublicKeyX509EncodedFromEVA   = fileEVAReaderInstance.cipherPublicKeyX509Encoded
        val cipherPrivateKeyPKCS8EncodedFromEVA = fileEVAReaderInstance.cipherPrivateKeyPKCS8Encoded

        if (
            userPadded.contentEquals(userPaddedByteArrayFromEVA)
        ) {
            logger.info { "User information matches. Synchronization rate at 10%" }

            try {
                val secretKeyGeneratorInstance = SecretKeyGenerator()
                secretKeyGeneratorInstance.generate(
                    password = password,
                    salt     = saltFromEVA
                )

                val secretKeyDecryptInstance = SecretKeyDecrypt()

                secretKeyDecryptInstance.decrypt(
                    cipherBytes       = cipherPublicKeyX509EncodedFromEVA,
                    cipherBytesLength = 176,
                    secretKey         = secretKeyGeneratorInstance.getSecretKey(),
                    initVectorBytes   = initVectorBytesFromEVA
                )

                publicKeyX509Encoded = secretKeyDecryptInstance.plainBytes

                secretKeyDecryptInstance.decrypt(
                    cipherBytes       = cipherPrivateKeyPKCS8EncodedFromEVA,
                    cipherBytesLength = 640,
                    secretKey         = secretKeyGeneratorInstance.getSecretKey(),
                    initVectorBytes   = initVectorBytesFromEVA
                )

                privateKeyPKCS8Encoded = secretKeyDecryptInstance.plainBytes

            } catch (throwable: Throwable) {
                logger.warn { "Wrong password input or fatal error has occurred." }
                logger.error { "Key pair information error. Synchronization stopped at 10%" }
                logger.error { throwable }
                return false
            }
            logger.info { "Key pair information obtained. Synchronization rate at 41.3%" }
            return true
        } else {
            logger.warn { "Internal user information does not match. Synchronization stopped at 0%" }
        }
        return false
    }
}