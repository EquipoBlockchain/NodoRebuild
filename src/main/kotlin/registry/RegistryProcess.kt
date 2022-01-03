package registry

import registry.process.isEVAValid
import registry.process.isProcessValid

fun registryProcess(
    user     : String,
    id       : String,
    password : String
) {
    println(user)
    println(id)
    println(password)

    val userByteArray     = user.toByteArray(Charsets.UTF_8)

    /*
    * TODO user padding
    * This has to be paralleled by the MAGI or the Entity database
    *
    * Options:
    * Fill with nulls  > Does not affect user size in EVA file
    * 2-16 byte blocks > Affects user size in EVA file
    * 3-8  byte blocks > Affects user size in EVA file
    * */

    val idByteArray       = id.toByteArray(Charsets.UTF_8)

    val passwordCharArray = password.toCharArray()

    if (
        isEVAValid(
            passwordCharArray = passwordCharArray,
            userByteArray     = userByteArray,
            idByteArray       = idByteArray
        )
    ) {
        // TODO Popup notification
        println("EVA is Valid")
    }

    if (
        isProcessValid(
            userByteArray = userByteArray,
            idByteArray   = idByteArray
        )
    ) {
        // TODO Popup notification
        println("Process is Valid")
    }
}