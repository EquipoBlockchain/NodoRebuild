package fileAccess.fileTypeEVA

import mu.KotlinLogging
import java.io.File
import java.io.FileInputStream

private val logger = KotlinLogging.logger {}

/**
 * This class allocates within named [ByteArray] variables the parts composing an EVA file.
 *
 * Variable names:
 * - [userPadded]                         Length:  16 bytes
 * - [id]                           Length:   8 bytes
 * - [salt]                         Length:  16 bytes
 * - [initVectorBytes]              Length:  16 bytes
 * - [cipherPublicKeyX509Encoded]   Length: 176 bytes
 * - [cipherPrivateKeyPKCS8Encoded] Length: 640 bytes
 *
 * @see FileInputStream
 */
class FileEVAReader {
    private lateinit var fileEVAToByteArray   : ByteArray

    lateinit var userPadded                   : ByteArray
    lateinit var id                           : ByteArray
    lateinit var salt                         : ByteArray
    lateinit var initVectorBytes              : ByteArray
    lateinit var cipherPublicKeyX509Encoded   : ByteArray
    lateinit var cipherPrivateKeyPKCS8Encoded : ByteArray

    private var index0 = 0
    private var index1 = index0 +  16 // user
    private var index2 = index1 +   8 // id
    private var index3 = index2 +  16 // salt
    private var index4 = index3 +  16 // initVectorBytes
    private var index5 = index4 + 176 // cipherPublicKeyX509Encoded
    private var index6 = index5 + 640 // cipherPrivateKeyPKCS8Encoded

    /**
     * Reads an EVA file and assigns each value to according to the expected index ranges.
     *
     * KotlinLogging Implemented.
     *
     * @param file File to be read.
     *
     * @see FileInputStream
     */
    fun readEVA(
        file : File,
    ) {
        try {
            if (file.exists()) {
                val inputStream              = FileInputStream(file)
                fileEVAToByteArray           = inputStream.readAllBytes()
                userPadded                   = fileEVAToByteArray.copyOfRange(index0, index1)
                id                           = fileEVAToByteArray.copyOfRange(index1, index2)
                salt                         = fileEVAToByteArray.copyOfRange(index2, index3)
                initVectorBytes              = fileEVAToByteArray.copyOfRange(index3, index4)
                cipherPublicKeyX509Encoded   = fileEVAToByteArray.copyOfRange(index4, index5)
                cipherPrivateKeyPKCS8Encoded = fileEVAToByteArray.copyOfRange(index5, index6)
                inputStream.close()
                logger.info { "${file.name} read successfully" }
            } else {
                logger.error { "${file.name} does not exist" }
            }
        }
        catch (throwable: Throwable) {
            logger.error { throwable }
        }
    }
}