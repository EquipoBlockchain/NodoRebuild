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

package live.feed

import fileAccess.fileTypeJSON.liveFeedUnit.FileJSONLiveFeedUnitReader
import fileAccess.fileTypeJSON.liveFeedUnit.toJSONLiveFeedUnitName
import fileAccess.fileTypeJSON.nodeListMAGI.FileJSONNodeListMAGIReader
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * This class allows allocating a [MutableList] of [FileJSONLiveFeedUnitReader] objects, value is assigned by calling
 * [generateLiveFeedInfo].
 *
 * - [liveInformationList] stores the list of live feed information units defined by [FileJSONLiveFeedUnitReader].
 */
class LiveInformation {
    var liveInformationList : MutableList<FileJSONLiveFeedUnitReader> = LinkedList<FileJSONLiveFeedUnitReader>()

    /**
     * TODO Document
     *
     * TODO KotlinLogging Implemented.
     */
    fun generateLiveFeedInfo(): Boolean {
        val fileJSONNodeListMAGIReader = FileJSONNodeListMAGIReader()

        if(fileJSONNodeListMAGIReader.readJSONNodeListMAGI()) {
            fileJSONNodeListMAGIReader.nodeMAGIInfo.forEach { nodeMAGI ->
                val liveUnitFileName = nodeMAGI.identifier.toJSONLiveFeedUnitName()

                val fileJSONLiveFeedUnitReader = FileJSONLiveFeedUnitReader()
                if(fileJSONLiveFeedUnitReader.readJSONLiveFeedUnit(liveUnitFileName)) {
                    liveInformationList.add(fileJSONLiveFeedUnitReader)
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