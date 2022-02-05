package login

import androidx.compose.runtime.MutableState
import fileAccess.*
import login.verifications.verificationProcess
import login.voting.votingProcess
import mu.KotlinLogging
import navigation.AppNavType
import java.io.File

private val logger = KotlinLogging.logger {}

private lateinit var id                     : String
private lateinit var publicKeyX509Encoded   : ByteArray
private lateinit var privateKeyPKCS8Encoded : ByteArray

/**
 * TODO Document
 *
 * TODO Splash Loading Screen
 *
 * TODO KotlinLogging Implemented.
 *
 * @param user User to be processed.
 * @param password Password to be processed.
 */
fun loginProcess(
    user         : String,
    password     : String,
    navItemState : MutableState<AppNavType>
) {
    val userByteArray       = user.toByteArray(Charsets.UTF_8)
    val userPaddedByteArray = addPadding(
        byteArray = userByteArray,
        size      = 16
    )
    val passwordCharArray   = password.toCharArray()

    val path     = getPath()
    val fileName = user.toEVAFileName()
    val format   = getFormat()

    val file = File("$path$fileName.$format")

    if (file.exists()) {
        val fileEVAKeyPairSyncInstance = FileEVAKeyPairSync()

        if (
            fileEVAKeyPairSyncInstance.synchronizeEVAKeyPair(
                file       = file,
                password   = passwordCharArray,
                userPadded = userPaddedByteArray
            )
        ) {
            id                     = fileEVAKeyPairSyncInstance.getIDString()
            publicKeyX509Encoded   = fileEVAKeyPairSyncInstance.getPublicKeyX509Encoded()
            privateKeyPKCS8Encoded = fileEVAKeyPairSyncInstance.getPrivateKeyPKCS8Encoded()
            logger.info { "Key pair information retrieved. Synchronization rate at 100%" }
            logger.info { "Pilot ready to vote. Log in options will be displayed." }
            navItemState.value = AppNavType.LOGIN_OPTIONS
        }
        else {
            logger.warn { "Not able to log with the credentials given." }
        }

    }
    else {
        logger.warn { "No matching user information found." }
    }
}

fun verificationSession() {
    logger.info { "Verification process started." }
    verificationProcess()
}

fun votingSession() {
    logger.info { "Voting process started." }
    votingProcess()
}

fun closeSession() {
    id                     = "00000000"
    publicKeyX509Encoded   = byteArrayOf(0x00)
    privateKeyPKCS8Encoded = byteArrayOf(0x00)
    logger.info { "Session closed. Values set to zero." }
}