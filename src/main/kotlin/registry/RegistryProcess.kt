package registry

import fileAccess.deleteEVAFile
import fileAccess.getFormat
import fileAccess.toEVAFileName
import fileAccess.getPath
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
    val userPaddedByteArray = addPadding(
        byteArray = userByteArray,
        size      = 16
    )
    val idByteArray       = id.toByteArray(Charsets.UTF_8)
    val passwordCharArray = password.toCharArray()

    val path     = getPath()
    val fileName = user.toEVAFileName()
    val format   = getFormat()

    val file = File("$path$fileName.$format")

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
        }
        else {
            // TODO Error notification

            logger.warn { "Process was not validated." }
            logger.warn { "${file.name} will be deleted." }
            deleteEVAFile(
                file = file
            )
        }
    }
    else {
        // TODO Error notification

        logger.error { "EVA file is not usable." }
    }
}