package fileAccess

import java.io.File
import java.io.FileInputStream
import java.io.IOException

fun getExampleMessage(): ByteArray {
    val messageByteArray: ByteArray
    try {
        val file = File("Information/message.md")
        if (file.exists()) {
            val inputStream  = FileInputStream(file)
            messageByteArray = inputStream.readAllBytes()
            inputStream.close()
            return messageByteArray
        }
    } catch (e: IOException) {
        println("While Reading: $e")
        //TODO convert to log
    }
    return "No message found".encodeToByteArray()
}