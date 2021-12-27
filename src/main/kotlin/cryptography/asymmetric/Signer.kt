package cryptography.asymmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Uses the provided [encodedPrivateKey] byte array, corresponding to the PKCS #8 encoded private key,
 * to sign the provided [message].
 *
 * The signature algorithm used is SHA-256 with RSA and MGF1 within the Bouncy Castle Provider.
 *
 * @param encodedPrivateKey Used by the [KeyFactory] to recover the [PrivateKey].
 * @param message Message to be signed.
 * @return [ByteArray] corresponding to the signature.
 * @see Signature
 * @see KeyFactory
 * @see PrivateKey
 */

fun signer(
    encodedPrivateKey: ByteArray,
    message: ByteArray
): ByteArray {
    //Add Bouncy Castle Provider
    Security.addProvider(BouncyCastleProvider())
    // RSA SSA - PSS 256
    val signatureInstance = Signature.getInstance("SHA256withRSAandMGF1", "BC")
    val keyFactoryInstance = KeyFactory.getInstance("RSA", "BC")
    val privateKeyPKCS8Spec = PKCS8EncodedKeySpec(encodedPrivateKey)
    val recoveredPrivateKey = keyFactoryInstance.generatePrivate(privateKeyPKCS8Spec)

    signatureInstance.initSign(recoveredPrivateKey, SecureRandom())
    signatureInstance.update(message)
    return signatureInstance.sign()
}