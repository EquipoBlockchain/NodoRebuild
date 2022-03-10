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

package fileAccess.fileTypeJSON.nodeListMAGI

import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

/**
 * TODO Document
 *
 * KotlinLogging Implemented.
 *
 */
fun createJSONNodeListMAGI(
    file : File,
): Boolean {
    try {
        val directory = File(file.parentFile.toURI())

        if (directory.mkdir()) {
            logger.info { "Directory: /${directory.name} was created" }
        } else if (directory.exists()) {
            logger.info { "Directory: /${directory.name} already exists" }
        } else {
            logger.warn { "Directory: /${directory.name} failed to be created" }
        }

        //TODO Obtain node list network data and create file

        return true
    } catch (throwable: Throwable) {
        logger.error { throwable }
    }
    return false
}