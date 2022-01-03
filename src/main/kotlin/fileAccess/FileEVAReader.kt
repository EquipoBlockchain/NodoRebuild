package fileAccess

import java.io.File
import java.io.FileInputStream
import java.io.IOException

class FileEVAReader {
    private lateinit var fileEVAToByteArray   : ByteArray

    lateinit var user                         : ByteArray
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

    fun readEVA(
        path     : String,
        fileName : String,
        format   : String
    ) {
        try {
            val file = File("$path$fileName.$format")
            if (file.exists()) {
                val inputStream              = FileInputStream(file)
                fileEVAToByteArray           = inputStream.readAllBytes()
                user                         = fileEVAToByteArray.copyOfRange(index0, index1)
                id                           = fileEVAToByteArray.copyOfRange(index1, index2)
                salt                         = fileEVAToByteArray.copyOfRange(index2, index3)
                initVectorBytes              = fileEVAToByteArray.copyOfRange(index3, index4)
                cipherPublicKeyX509Encoded   = fileEVAToByteArray.copyOfRange(index4, index5)
                cipherPrivateKeyPKCS8Encoded = fileEVAToByteArray.copyOfRange(index5, index6)
            }
        } catch (e: IOException) {
            println("While Reading: $e")
        }
    }
}