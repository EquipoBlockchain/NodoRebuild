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

package live

import live.feed.LiveInformation
import live.feed.ProcessResults
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

val processResults = mutableListOf<ProcessResults>()

//TODO Document
fun liveProcess() : Boolean {
    val liveInformation = LiveInformation()

    val voteListProcessDefinitions = mutableListOf<String>()
    val voteListProcessResults     = mutableListOf<Float>()

    if(liveInformation.generateLiveFeedInfo()) {
        liveInformation.liveInformationList.forEach { liveFeedUnit ->
            liveFeedUnit.voteList.forEach { liveFeedUnitEntryVotes ->
                val index = voteListProcessDefinitions.indexOf(liveFeedUnitEntryVotes.selectedOptionDefinition)
                if(index == -1) {
                    voteListProcessDefinitions.add(liveFeedUnitEntryVotes.selectedOptionDefinition)
                    voteListProcessResults.add(1F)
                } else {
                    voteListProcessResults[index] += 1F
                }
            }
        }
        for (index in 0 until voteListProcessResults.size) {
            voteListProcessResults[index] /= 3F
        }
        voteListProcessDefinitions.forEachIndexed { index, definition ->
            processResults.add(
                ProcessResults(
                    definition,
                    voteListProcessResults[index].toInt()
                )
            )
        }
    }
    //Debug
    println(voteListProcessDefinitions.toString() +" "+ voteListProcessResults.toString())
    return true
}