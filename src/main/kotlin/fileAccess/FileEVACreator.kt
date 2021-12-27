package fileAccess

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun createEVA(path: String, fileName: String, format: String, input: ByteArray): Boolean {
    try {
        val folder = File(path)
        val file   = File("$path$fileName.$format")

        println("MK Dir: ${folder.mkdir()}")

        if (file.createNewFile()) {
            val outputStream = FileOutputStream(file)
            outputStream.run {
                write(input)
                close()
            }
            return true
        }
    } catch (e: IOException) {
        println("While Creating: $e")
    }
    return false
}