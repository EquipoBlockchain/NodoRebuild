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

package fileAccess.fileTypeEVA

import mu.KotlinLogging
import java.io.File
import java.io.FileOutputStream

private val logger = KotlinLogging.logger {}

/**
 * Creates and writes to an EVA file all the inputs introduced.
 *
 * KotlinLogging Implemented.
 *
 * @param file File to be read. If the directory included does not exist, it is created.
 * @param userPadded Byte array to be written (Size: 16 bytes).
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
    file                         : File,
    userPadded                   : ByteArray,
    id                           : ByteArray,
    salt                         : ByteArray,
    initVectorBytes              : ByteArray,
    cipherPublicKeyX509Encoded   : ByteArray,
    cipherPrivateKeyPKCS8Encoded : ByteArray
): Boolean {
    try {
        val directory = File(file.parentFile.toURI())

        if (directory.mkdir()) {
            logger.info { "Directory: /${directory.name} was created." }
        } else if (directory.exists()) {
            logger.info { "Directory: /${directory.name} already exists." }
        } else {
            logger.warn { "Directory: /${directory.name} failed to be created." }
        }

        if (file.createNewFile()) {
            val outputStream = FileOutputStream(file)
            outputStream.runCatching {
                write(userPadded)
                write(id)
                write(salt)
                write(initVectorBytes)
                write(cipherPublicKeyX509Encoded)
                write(cipherPrivateKeyPKCS8Encoded)
                close()
            }.onSuccess {
                logger.info { "Writing to ${file.name} successful." }
            }.onFailure { throwable ->
                logger.error { "Writing to ${file.name} failed." }
                logger.error { throwable }
            }
            return true
        } else if (file.exists()) {
            logger.warn { "File: ${file.name} already exists." }
        } else {
            logger.error { "File: ${file.name} could not be created." }
        }
    }
    catch (throwable: Throwable) {
        logger.error { throwable }
    }
    return false
}