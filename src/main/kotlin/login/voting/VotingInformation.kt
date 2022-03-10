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
            //TODO Verify Signature
            return true
        } else {
            //TODO
        }
        return false
    }
}
