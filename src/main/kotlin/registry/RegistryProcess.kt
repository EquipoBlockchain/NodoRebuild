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

package registry

import fileAccess.fileTypeEVA.deleteEVAFile
import fileAccess.fileTypeEVA.getEVAFormat
import fileAccess.fileTypeEVA.toEVAFileName
import fileAccess.fileTypeEVA.getEVAPath
import mu.KotlinLogging
import registry.validations.isEVAValid
import registry.validations.isProcessValid
import fileAccess.addPadding
import java.io.File

private val logger = KotlinLogging.logger {}

/**
 * Starts the EVA file validation and the MAGI process validation.
 *
 * KotlinLogging Implemented.
 *
 * @param user User to be processed.
 * @param id  Identifier to be processed.
 * @param password Password to be processed.
 */
fun registryProcess(
    user     : String,
    id       : String,
    password : String
) {
    val userByteArray       = user.toByteArray(Charsets.UTF_8)
    val userPaddedByteArray = userByteArray.addPadding(size = 16)
    val idByteArray       = id.toByteArray(Charsets.UTF_8)
    val passwordCharArray = password.toCharArray()

    val path     = getEVAPath()
    val fileName = user.toEVAFileName()
    val format   = getEVAFormat()

    val file = File("$path$fileName$format")

    if (
        isEVAValid(
            file       = file,
            password   = passwordCharArray,
            userPadded = userPaddedByteArray,
            id         = idByteArray
        )
    ) {
        // TODO Popup notification

        logger.info { "EVA file is usable." }

        if (
            isProcessValid(
                userPaddedByteArray = userPaddedByteArray,
                idByteArray         = idByteArray
            )
        ) {
            // TODO Popup notification

            logger.info { "Process was validated successfully." }
        } else {
            // TODO Error notification

            logger.warn { "Process was not validated." }
            logger.warn { "${file.name} will be deleted." }
            deleteEVAFile(
                file = file
            )
        }
    } else {
        // TODO Error notification

        logger.error { "EVA file is not usable." }
    }
}