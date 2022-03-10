/**
 * Copyright 2021 Kyle Elbjorn
 *
 * This file is part of GEHIRN Node.
 *
 * GEHIRN Node is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * GEHIRN Node is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with GEHIRN Node.
 * If not, see <https://www.gnu.org/licenses/>.
 */

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
        } else {
            logger.error { "${file.name} does not exist" }
        }
    }
    catch (throwable: Throwable) {
        logger.error { throwable }
    }
    logger.info { "Default response will be used instead" }
    return "No message found".encodeToByteArray()
}