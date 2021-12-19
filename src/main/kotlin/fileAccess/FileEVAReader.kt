package fileAccess

import java.io.File
import java.io.FileInputStream
import java.io.IOException

class FileEVAReader {
    lateinit var fileEVAToByteArray: ByteArray

    fun readEVA(path: String, fileName: String, format: String) {
        try {
            val file = File("$path$fileName.$format")
            if (file.exists()) {
                val inputStream = FileInputStream(file)
                fileEVAToByteArray = inputStream.readAllBytes()
            }
        } catch (e: IOException) {
            println("While Reading: $e")
        }
    }
}