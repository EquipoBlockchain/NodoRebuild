package fileAccess

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun createEVA(path: String, fileName: String, format: String, input: ByteArray): Boolean {
    try {
        val file = File("$path$fileName.$format")

        if (file.createNewFile()) {
            val outputStream = FileOutputStream(file)
            outputStream.write(input)
            outputStream.close()
            return true
        }
    } catch (e: IOException) {
        println("While Creating: $e")
    }
    return false
}