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

package fileAccess

/**
 * Fills the empty spaces in a [ByteArray] with 0x00 bytes, until the size determined is reached.
 *
 * @param size Desired final array size.
 * @return [ByteArray] Padded byte array.
 */
fun ByteArray.addPadding(
    size : Int
): ByteArray {
    val paddedByteArray = ByteArray(size)
    for (i in 0 until size) {
        if (i < this.size) {
            paddedByteArray[i] = this[i]
        } else {
            paddedByteArray[i] = 0x00
        }
    }
    return paddedByteArray
}