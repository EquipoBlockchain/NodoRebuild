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