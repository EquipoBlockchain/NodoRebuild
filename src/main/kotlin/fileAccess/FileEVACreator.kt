package fileAccess

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        // TODO Make logs
        println("MK Dir: ${folder.mkdir()}")

        if (file.createNewFile()) {
            val outputStream = FileOutputStream(file)
            outputStream.run {
                write(user)
                write(id)
                write(salt)
                write(initVectorBytes)
                write(cipherPublicKeyX509Encoded)
                write(cipherPrivateKeyPKCS8Encoded)
                close()
            }
            return true
        }
    } catch (e: IOException) {
        println("While Creating: $e")
    }
    return false
}