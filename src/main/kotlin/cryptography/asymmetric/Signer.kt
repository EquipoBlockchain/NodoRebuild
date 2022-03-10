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
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Uses the provided [privateKeyPKCS8Encoded] byte array, corresponding to the PKCS #8 encoded private key,
 * to sign the provided [message].
 *
 * The signature algorithm used is SHA-256 with RSA and MGF1 within the Bouncy Castle Provider.
 *
 * @param privateKeyPKCS8Encoded Used by the [KeyFactory] to recover the [PrivateKey].
 * @param message Message to be signed.
 * @return [ByteArray] corresponding to the signature.
 * @see PrivateKey
 * @see Signature
 * @see KeyFactory
 * @see PKCS8EncodedKeySpec
 */
fun signer(
    privateKeyPKCS8Encoded : ByteArray,
    message                : ByteArray
): ByteArray {
    // Add Bouncy Castle Provider
    Security.addProvider(BouncyCastleProvider())

    // RSA SSA - PSS 256
    val signatureInstance   = Signature.getInstance("SHA256withRSAandMGF1", "BC")
    val keyFactoryInstance  = KeyFactory.getInstance("RSA", "BC")
    val privateKeyPKCS8Spec = PKCS8EncodedKeySpec(privateKeyPKCS8Encoded)
    val recoveredPrivateKey = keyFactoryInstance.generatePrivate(privateKeyPKCS8Spec)

    signatureInstance.initSign(recoveredPrivateKey, SecureRandom())
    signatureInstance.update(message)
    return signatureInstance.sign()
}