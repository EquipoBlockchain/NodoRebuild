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

package login

import androidx.compose.runtime.MutableState
import com.fasterxml.jackson.databind.ObjectMapper
import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import cryptography.base64.toBase64
import fileAccess.addPadding
import fileAccess.fileTypeEVA.FileEVAKeyPairSync
import fileAccess.fileTypeEVA.getEVAFormat
import fileAccess.fileTypeEVA.getEVAPath
import fileAccess.fileTypeEVA.toEVAFileName
import fileAccess.fileTypeJSON.voteSelectionUnit.*
import login.verifications.verificationProcess
import mu.KotlinLogging
import navigation.AppNavType
import java.io.File
import java.time.LocalDateTime


private val logger = KotlinLogging.logger {}

private lateinit var userPaddedByteArray    : ByteArray
private lateinit var id                     : String
private lateinit var publicKeyX509Encoded   : ByteArray
private lateinit var privateKeyPKCS8Encoded : ByteArray

/**
 * TODO Document
 *
 * TODO Splash Loading Screen
 *
 * KotlinLogging Implemented.
 *
 * @param user User to be processed.
 * @param password Password to be processed.
 */
fun loginProcess(
    user         : String,
    password     : String,
    navItemState : MutableState<AppNavType>
) {
    val userByteArray = user.toByteArray(Charsets.UTF_8)

    userPaddedByteArray = userByteArray.addPadding(size = 16)

    val passwordCharArray   = password.toCharArray()

    val path     = getEVAPath()
    val fileName = user.toEVAFileName()
    val format   = getEVAFormat()

    val file = File("$path$fileName$format")

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
        } else {
            logger.warn { "Not able to log with the credentials given." }
        }

    } else {
        logger.warn { "No matching user information found." }
    }
}

fun verificationSession() {
    logger.info { "Verification process started." }
    verificationProcess()
}

/**
 * Terminates a login session, setting all private values to zero.
 *
 * KotlinLogging Implemented.
 */
fun closeSession() {
    userPaddedByteArray    = byteArrayOf(0x00)
    id                     = "00000000"
    publicKeyX509Encoded   = byteArrayOf(0x00)
    privateKeyPKCS8Encoded = byteArrayOf(0x00)
    logger.info { "Session closed. Values set to zero." }
}

fun votingSessionVerifiedSignature(
    blockNumber              : Int,
    selectedOptionNumber     : Int,
    selectedOptionDefinition : String
) {
    val votingTimestamp = LocalDateTime.now()

    val path     = getJSONVoteSelectionUnitPath()
    val fileName = ("$blockNumber").toJSONVoteSelectionUnitName(votingTimestamp)
    val format   = getJSONVoteSelectionUnitFormat()

    val file = File("$path$fileName$format")

    val voteSelectionSoul = VoteSelectionSoul(
        blockNumber              = blockNumber,
        selectedOptionNumber     = selectedOptionNumber,
        selectedOptionDefinition = selectedOptionDefinition,
        votingTimestamp          = votingTimestamp.toString()
    )

    val message = voteSelectionSoul.toString().toByteArray(Charsets.UTF_8)
    //TODO remove
    println("Debug voteSelectionSoul.toString() : $voteSelectionSoul")

    val signature = signer(
        privateKeyPKCS8Encoded = privateKeyPKCS8Encoded,
        message                = message
    )

    if(
        verifier(
            publicKeyX509Encoded = publicKeyX509Encoded,
            message              = message,
            signature            = signature
        )
    ) {
        logger.info { "Signature for ${file.name} verified." }

        val voteSelectionUnit = VoteSelectionUnit(
            userBase64        = userPaddedByteArray.toBase64(),
            id                = id,
            voteSelectionSoul = voteSelectionSoul,
            signature         = signature.toBase64()
        )

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
                ObjectMapper().runCatching {
                    writeValue(file, voteSelectionUnit)
                }.onSuccess {
                    logger.info { "Writing to ${file.name} successful." }
                }.onFailure { throwable ->
                    logger.error { "Writing to ${file.name} failed." }
                    logger.error { throwable }
                }
            }
        } catch (throwable : Throwable) {
            logger.error { throwable }
        }
    } else {
        logger.error { "Signature for ${file.name} could not be verified." }
    }
}