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

package fileAccess.fileTypeJSON.voteSelectionUnit

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

// TODO subject to change

/**
 * Directory in the system, where the vote selection JSON file is stored (Expected to end with / character).
 *
 * @return [String]
 */
fun getJSONVoteSelectionUnitPath(): String {
    return "information/voteSelection/"
}

/**
 * String is processed to replace invalid character for naming files.
 *
 * @return [String]
 */
fun String.replaceInvalidChars(): String {
    return this.replace(
        oldChar = ':',
        newChar = '-'
    )
}

/**
 * String is processed into a vote selection JSON file name.
 *
 * @param votingTimestamp Referential timestamp.
 * @return [String]
 */
fun String.toJSONVoteSelectionUnitName(
    votingTimestamp: LocalDateTime
): String {
    val processedTimestamp = votingTimestamp.truncatedTo(ChronoUnit.SECONDS).toString().replaceInvalidChars()
    return "VSU_${this}_${processedTimestamp}"
}

/**
 * Designated extension of the vote selection JSON file.
 *
 * @return [String]
 */
fun getJSONVoteSelectionUnitFormat(): String {
    return ".json"
}