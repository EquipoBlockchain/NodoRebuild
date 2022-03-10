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