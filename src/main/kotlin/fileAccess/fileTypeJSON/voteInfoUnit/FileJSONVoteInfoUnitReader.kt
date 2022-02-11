package fileAccess.fileTypeJSON.voteInfoUnit

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

/**
 * This class allows allocating one [Integer] variable, two [String] objects, and one [VoteInfoUnitEntry] object, value
 * is assigned by calling [readJSONVoteInfoUnit].
 *
 * - [originID] represents the identifier of the node that sends the entry.
 * - [timeStamp] represents the date and time when the entry was generated.
 * - [entry]
 * - [signature]
 */
class FileJSONVoteInfoUnitReader {
    var originID : Int = 0

    lateinit var timeStamp : String
    lateinit var entry     : VoteInfoUnitEntry
    lateinit var signature : String

    /**
     * TODO Document
     *
     * TODO Log
     *
     */
    fun readJSONVoteInfoUnit(
        voteUnitFileName : String,
    ): Boolean {
        val voteUnitPath   = getJSONVoteInfoUnitPath()
        val voteUnitFormat = getJSONVoteInfoUnitFormat()
        val file           = File("$voteUnitPath$voteUnitFileName$voteUnitFormat")

        if (file.exists()) {
            logger.info { "${file.name} exists." }

            val voteInfoUnit = jacksonObjectMapper().readValue<FileJSONVoteInfoUnitReader>(file)
            originID  = voteInfoUnit.originID
            timeStamp = voteInfoUnit.timeStamp
            entry     = voteInfoUnit.entry
            signature = voteInfoUnit.signature

            //TODO File update on old (compared to LocalDateTime.now())

            return true
        } else {
            logger.error { "${file.name} does not exist. File creation will be attempted." }
            createJSONVoteInfoUnit(file)
            //TODO On creation failure & retry process
        }
        return false
    }
}