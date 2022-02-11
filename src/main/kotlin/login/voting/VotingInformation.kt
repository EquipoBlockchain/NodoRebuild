package login.voting

import fileAccess.fileTypeJSON.nodeListMAGI.FileJSONNodeListMAGIReader
import fileAccess.fileTypeJSON.voteInfoUnit.FileJSONVoteInfoUnitReader
import fileAccess.fileTypeJSON.voteInfoUnit.toJSONVoteInfoUnitName
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * This class allows allocating a [MutableList] of [FileJSONVoteInfoUnitReader] objects, value is assigned by calling
 * [generateVotingInfo].
 *
 * - [voteInformationList] stores the list of vote information units defined by [FileJSONVoteInfoUnitReader].
 */
class VotingInformation {
    var voteInformationList : MutableList<FileJSONVoteInfoUnitReader> = LinkedList<FileJSONVoteInfoUnitReader>()

    /**
     * TODO Document
     *
     * TODO KotlinLogging Implemented.
     */
    fun generateVotingInfo(): Boolean {
        val fileJSONNodeListMAGIReader = FileJSONNodeListMAGIReader()

        if(fileJSONNodeListMAGIReader.readJSONNodeListMAGI()) {
            fileJSONNodeListMAGIReader.nodeMAGIInfo.forEach { nodeMAGI ->
                val voteUnitFileName = nodeMAGI.identifier.toJSONVoteInfoUnitName()

                val fileJSONVoteInfoUnitReader = FileJSONVoteInfoUnitReader()
                if(fileJSONVoteInfoUnitReader.readJSONVoteInfoUnit(voteUnitFileName)) {
                    voteInformationList.add(fileJSONVoteInfoUnitReader)
                }
            }
            return true
        } else {
            //TODO
        }
        return false
    }
}
