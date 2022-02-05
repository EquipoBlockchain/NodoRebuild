package fileAccess

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
        }
        else if (file.exists()) {
            logger.error { "${file.name} could not be deleted, and still exists." }
        }
        else {
            logger.warn { "${file.name} does not exist." }
        }
    }
    catch (throwable: Throwable) {
        logger.error { throwable }
    }
}