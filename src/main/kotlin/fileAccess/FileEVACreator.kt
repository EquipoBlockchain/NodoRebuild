package fileAccess

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Creates and writes to an EVA file all the inputs introduced.
 *
 * @param path Directory in the system, where the EVA file is stored (Expected to end with / character).
 * If the directory introduced does not exist, it is created.
 * @param fileName Name of the EVA file to be read.
 * @param format Designated extension of the EVA file.
 * @param user Byte array to be written (Size: 16 bytes).
 * @param id Byte array to be written (Size: 8 bytes).
 * @param salt Byte array to be written (Size: 16 bytes).
 * @param initVectorBytes Byte array to be written (Size: 16 bytes).
 * @param cipherPublicKeyX509Encoded Byte array to be written (Size: 176 bytes).
 * @param cipherPrivateKeyPKCS8Encoded Byte array to be written (Size: 640 bytes).
 *
 * @return [Boolean] confirming file creation.
 *
 * @see File
 * @see FileOutputStream
 */
fun createEVA(
    path                         : String,
    fileName                     : String,
    format                       : String,
    user                         : ByteArray,
    id                           : ByteArray,
    salt                         : ByteArray,
    initVectorBytes              : ByteArray,
    cipherPublicKeyX509Encoded   : ByteArray,
    cipherPrivateKeyPKCS8Encoded : ByteArray
): Boolean {
    try {
        val folder = File(path)
        val file   = File("$path$fileName.$format")

        println("MK Dir: ${folder.mkdir()}")
        // TODO Make logs

        if (file.createNewFile()) {
            val outputStream = FileOutputStream(file)
            outputStream.runCatching {
                write(user)
                write(id)
                write(salt)
                write(initVectorBytes)
                write(cipherPublicKeyX509Encoded)
                write(cipherPrivateKeyPKCS8Encoded)
                close()
            }.onFailure {
                println("While Creating: $it")
                // TODO Make logs
            }
            return true
        }
    } catch (e: IOException) {
        println("While Creating: $e")
        // TODO Make logs
    }
    return false
}