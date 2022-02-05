package fileAccess

import mu.KotlinLogging
import java.io.File
import java.io.FileInputStream

private val logger = KotlinLogging.logger {}

/**
 * Retrieves the contents stored in "message.md".
 *
 * If the file is not available, it returns a default response.
 *
 * KotlinLogging Implemented.
 *
 * @return [ByteArray] of the processed file or the default response.
 */
fun getExampleMessage(): ByteArray {
    val messageByteArray: ByteArray
    try {
        val file = File("information/message.md")
        if (file.exists()) {
            val inputStream  = FileInputStream(file)
            messageByteArray = inputStream.readAllBytes()
            inputStream.close()
            logger.info { "${file.name} retrieved" }
            return messageByteArray
        }
        else {
            logger.error { "${file.name} does not exist" }
        }
    }
    catch (throwable: Throwable) {
        logger.error { throwable }
    }
    logger.info { "Default response will be used instead" }
    return "No message found".encodeToByteArray()
}