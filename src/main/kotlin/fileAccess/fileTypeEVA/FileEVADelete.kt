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

private val logger = KotlinLogging.logger {}

/**
 * Deletes the file specified.
 *
 * KotlinLogging Implemented.
 *
 * @param file File to be deleted.
 */
fun deleteEVAFile(
    file : File
) {
    try {
        if (file.delete()) {
            logger.info { "${file.name} deleted successfully" }
        } else if (file.exists()) {
            logger.error { "${file.name} could not be deleted, and still exists." }
        } else {
            logger.warn { "${file.name} does not exist." }
        }
    }
    catch (throwable: Throwable) {
        logger.error { throwable }
    }
}