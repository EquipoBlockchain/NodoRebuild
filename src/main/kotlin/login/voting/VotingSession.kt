package login.voting

import fileAccess.fileTypeJSON.voteInfoUnit.VoteInfoUnitEntryOptions
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

var pattern     = ""
var blockNumber = 0
var description = ""
var question    = ""
var options : MutableList<VoteInfoUnitEntryOptions> = LinkedList<VoteInfoUnitEntryOptions>()

/**
 * TODO Document
 *
 * TODO KotlinLogging Implemented.
 */
fun votingSession(): Boolean {
    //TODO verify active voting session
    //TODO verify has already voted
    logger.info { "Voting process started." }
    val votingProcess = VotingProcess()
    votingProcess.votingConsensus()
    if(votingProcess.isDummyInitialized()) {
        blockNumber = votingProcess.dummy.blockNumber
        description = votingProcess.dummy.description
        question    = votingProcess.dummy.question
        options     = votingProcess.dummy.options
        return true
    }
    return false
}