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

package cryptography.asymmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * This class allows allocating a [KeyPair] object initialized outside the constructor, requiring to call [generate]
 * first.
 *
 * Each key can only be obtained as a [ByteArray] resulting of its respective encoding.
 *
 * - Public Key is encoded according to the X.509 standard.
 * - Private Key is encoded according to the PKCS #8 standard.
 *
 * @see KeyPair
 * @see X509EncodedKeySpec
 * @see PKCS8EncodedKeySpec
 */
class KeyPairGenerator {
    private lateinit var keyPair: KeyPair

    /**
     * Returns the public key encoded according to the X.509 standard as a byte array.
     *
     * @return [ByteArray]
     * @see X509EncodedKeySpec
     */
    fun getPublicKeyX509Encoded(): ByteArray {
        val publicKeyX509 = X509EncodedKeySpec(keyPair.public.encoded)
        return publicKeyX509.encoded
    }

    /**
     * Returns the private key encoded according to the PKCS #8 standard as a byte array.
     *
     * @return [ByteArray]
     * @see PKCS8EncodedKeySpec
     */
    fun getPrivateKeyPKCS8Encoded(): ByteArray {
        val privateKeyPKCS8 = PKCS8EncodedKeySpec(keyPair.private.encoded)
        return privateKeyPKCS8.encoded
    }

    /**
     * Generates a new cryptographic key pair using the RSA algorithm within the Bouncy Castle Provider. Initialized
     * with a key size of 1024 bits.
     *
     * @see KeyPairGenerator
     */
    fun generate() {
        // Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val keyGen = KeyPairGenerator.getInstance("RSA", "BC")
        keyGen.initialize(1024)
        keyPair = keyGen.generateKeyPair()
    }
}