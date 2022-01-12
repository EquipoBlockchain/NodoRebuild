package registry

import fileAccess.deleteEVAFile
import fileAccess.getFormat
import fileAccess.getNewFileName
import fileAccess.getPath
import registry.process.isEVAValid
import registry.process.isProcessValid
import registry.user.addPadding

fun registryProcess(
    user     : String,
    id       : String,
    password : String
) {
    val userByteArray       = user.toByteArray(Charsets.UTF_8)
    val userPaddedByteArray = addPadding(userByteArray)
    val idByteArray         = id.toByteArray(Charsets.UTF_8)
    val passwordCharArray   = password.toCharArray()

    val path     = getPath()
    val fileName = getNewFileName(user)
    val format   = getFormat()

    if (
        isEVAValid(
            path                = path,
            fileName            = fileName,
            format              = format,
            passwordCharArray   = passwordCharArray,
            userPaddedByteArray = userPaddedByteArray,
            idByteArray         = idByteArray
        )
    ) {
        // TODO Popup notification
        println("EVA is valid")
    }
    else {
        // TODO Error notification
        println("EVA is NOT valid")
    }

    if (
        isProcessValid(
            userPaddedByteArray = userPaddedByteArray,
            idByteArray         = idByteArray
        )
    ) {
        // TODO Popup notification
        println("Process is valid")
    }
    else {
        // TODO Error notification
        println("Process is NOT valid")
        deleteEVAFile(
            path     = path,
            fileName = fileName,
            format   = format
        )
    }
}