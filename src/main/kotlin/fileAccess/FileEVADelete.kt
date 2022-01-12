package fileAccess

import java.io.File
import java.io.IOException

fun deleteEVAFile(
    path     : String,
    fileName : String,
    format   : String
) {
    val file = File("$path$fileName.$format")
    try {
        println("Was file deleted? : ${file.delete()}")
        //TODO convert to log
    }
    catch (e: IOException){
        println(e)
        //TODO convert to log
    }
}