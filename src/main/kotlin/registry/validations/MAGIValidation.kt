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

package registry.validations

import fileAccess.addPadding

fun isProcessValid(
    userPaddedByteArray : ByteArray,
    idByteArray         : ByteArray
): Boolean {
    // TODO Verification with each MAGI system
    // Placeholder user : JulioRegalado
    // Placeholder id   : 12345678

    if (
        // TODO Use response from MAGI
        userPaddedByteArray.contentEquals("JulioRegalado".toByteArray(Charsets.UTF_8).addPadding(size = 16)) &&
        idByteArray.contentEquals("12345678".toByteArray(Charsets.UTF_8))
    ) {
        return true
    }

    return false
}