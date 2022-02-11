package fileAccess.fileTypeJSON.nodeListMAGI

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

/**
 * This class allows allocating one [String] object, and one [MutableList] of [NodeInfoMAGI] objects, initialized
 * outside the constructor, requiring to call [readJSONNodeListMAGI] first.
 *
 * - [lastUpdate] represents the date and time the information in the file was stored.
 * - [nodeMAGIInfo] stores a list of [NodeInfoMAGI] objects, corresponding to the individual information of each MAGI
 * node.
 */
class FileJSONNodeListMAGIReader {
    lateinit var lastUpdate   : String
    lateinit var nodeMAGIInfo : MutableList<NodeInfoMAGI>

    /**
     * TODO Document
     *
     * TODO Log
     *
     */
    fun readJSONNodeListMAGI(): Boolean {
        val path     = getJSONNodeListMAGIPath()
        val fileName = getJSONNodeListMAGIName()
        val format   = getJSONNodeListMAGIFormat()

        val file = File("$path$fileName$format")

        if (file.exists()) {
            logger.info { "${file.name} exists." }

            val nodeMAGIList = jacksonObjectMapper().readValue<FileJSONNodeListMAGIReader>(file)
            lastUpdate   = nodeMAGIList.lastUpdate
            nodeMAGIInfo = nodeMAGIList.nodeMAGIInfo

            //TODO File update on old (compared to LocalDateTime.now())

            return true
        } else {
            logger.error { "${file.name} does not exist. File creation will be attempted." }
            createJSONNodeListMAGI(file)
            //TODO On creation failure & retry process
        }
        return false
    }
}