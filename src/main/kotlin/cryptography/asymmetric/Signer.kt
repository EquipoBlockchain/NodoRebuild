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