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

package fileAccess.fileTypeJSON.liveFeedUnit

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

/**
 * TODO Document
 */
class FileJSONLiveFeedUnitReader {
    var originID    : Int = 0
    var blockNumber : Int = 0

    lateinit var timestamp : String
    lateinit var voteList  : List<LiveFeedUnitEntryVotes>
    lateinit var signature : String

    /**
     * TODO Document
     *
     * TODO Log
     *
     */
    fun readJSONLiveFeedUnit(
        liveFeedFileName : String,
    ): Boolean {
        val liveFeedPath   = getJSONLiveFeedUnitPath()
        val liveFeedFormat = getJSONLiveFeedUnitFormat()
        val file           = File("$liveFeedPath$liveFeedFileName$liveFeedFormat")

        if (file.exists()) {
            logger.info { "${file.name} exists." }

            val liveFeedUnit = jacksonObjectMapper().readValue<FileJSONLiveFeedUnitReader>(file)
            originID    = liveFeedUnit.originID
            blockNumber = liveFeedUnit.blockNumber
            timestamp   = liveFeedUnit.timestamp
            voteList    = liveFeedUnit.voteList
            signature   = liveFeedUnit.signature

            //TODO File update on old (compared to LocalDateTime.now())
            //TODO Not initialized = Info error
            return true
        } else {
            logger.error { "${file.name} does not exist. File creation will be attempted." }
            createJSONLiveFeedUnit(file)
            //TODO On creation failure & retry process
        }
        return false
    }
}