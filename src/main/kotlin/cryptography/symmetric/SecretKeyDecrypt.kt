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

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * This class allows to decrypt a given input, obtaining a deciphered [ByteArray] and its calculated length as an
 * [Integer].
 *
 * - [paddedPlainBytes] Used internally to store the byte array resulting from the decryption.
 * - [plainBytes] Used to store the decrypted byte array after padding is removed.
 * - [plainBytesLength] Used to store the calculated length to determine byte array size after padding is removed.
 *
 * @see Cipher
 */

class SecretKeyDecrypt {
    private lateinit var paddedPlainBytes : ByteArray
    lateinit var plainBytes               : ByteArray
    var plainBytesLength                  = 0

    /**
     * Decrypts a provided byte array with the AES CBC algorithm and a PKCS #7 padding utilizing the Bouncy Castle
     * Provider.
     *
     * @param cipherBytes The [ByteArray] to be encrypted.
     * @param cipherBytesLength The size of the encrypted [ByteArray].
     * @param secretKey The [SecretKey] used for encryption.
     * @param initVectorBytes The initialization bytes to be used in the decryption.
     * @see Cipher
     * @see IvParameterSpec
     */
    fun decrypt(
        cipherBytes       : ByteArray,
        cipherBytesLength : Int,
        secretKey         : SecretKey,
        initVectorBytes   : ByteArray
    ) {
        //Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val decipher       = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")
        val initVectorSpec = IvParameterSpec(initVectorBytes)
        decipher.init(Cipher.DECRYPT_MODE, secretKey, initVectorSpec)

        paddedPlainBytes =  ByteArray(decipher.getOutputSize(cipherBytesLength))
        plainBytesLength =  decipher.update(cipherBytes, 0, cipherBytesLength, paddedPlainBytes, 0)
        plainBytesLength += decipher.doFinal(paddedPlainBytes, plainBytesLength)
        plainBytes       =  paddedPlainBytes.copyOfRange(0, plainBytesLength)
    }
}