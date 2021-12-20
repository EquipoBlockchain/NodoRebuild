package fileAccess

import java.io.File
import java.io.FileInputStream
import java.io.IOException

fun getExampleMessage(): ByteArray {
    val messageByteArray: ByteArray
    try {
        val file = File("Information/message.md")
        if (file.exists()) {
            val inputStream = FileInputStream(file)
            messageByteArray = inputStream.readAllBytes()
            return messageByteArray
        }
    } catch (e: IOException) {
        println("While Reading: $e")
    }
    return "No message found".encodeToByteArray()
}