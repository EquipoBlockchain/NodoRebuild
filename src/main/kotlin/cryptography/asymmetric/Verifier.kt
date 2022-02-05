package cryptography.asymmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.*
import java.security.spec.X509EncodedKeySpec

/**
 * Uses the provided [publicKeyX509Encoded] byte array, corresponding to the X.509 encoded public key,
 * and the provided [signature] byte array to verify the provided [message].
 *
 * The signature algorithm used is SHA-256 with RSA and MGF1 within the Bouncy Castle Provider.
 *
 * @param publicKeyX509Encoded Used by the [KeyFactory] to recover the [PublicKey].
 * @param message Message to be verified.
 * @param signature Signature corresponding to the message.
 * @return [Boolean] where true means the message was correctly verified.
 * @see PublicKey
 * @see Signature
 * @see KeyFactory
 * @see X509EncodedKeySpec
 */
fun verifier(
    publicKeyX509Encoded : ByteArray,
    message              : ByteArray,
    signature            : ByteArray
): Boolean {
    // Add Bouncy Castle Provider
    Security.addProvider(BouncyCastleProvider())

    // RSA SSA - PSS 256
    val signatureInstance  = Signature.getInstance("SHA256withRSAandMGF1", "BC")
    val keyFactoryInstance = KeyFactory.getInstance("RSA", "BC")
    val publicKeyX509Spec  = X509EncodedKeySpec(publicKeyX509Encoded)
    val recoveredPublicKey = keyFactoryInstance.generatePublic(publicKeyX509Spec)

    signatureInstance.initVerify(recoveredPublicKey)
    signatureInstance.update(message)
    return signatureInstance.verify(signature)
}