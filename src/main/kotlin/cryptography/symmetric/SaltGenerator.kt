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

package cryptography.symmetric

import java.security.SecureRandom

/**
 * Generates an array of 16 bytes, using a [SecureRandom] object
 * to be used as cryptographic salt.
 *
 * @return [ByteArray] corresponding to a 16-byte salt.
 * @see SecureRandom
 */
fun getNewSalt(): ByteArray {
    val secureRandom = SecureRandom()
    val salt         = ByteArray(size = 16)

    secureRandom.nextBytes(salt)

    return salt
}