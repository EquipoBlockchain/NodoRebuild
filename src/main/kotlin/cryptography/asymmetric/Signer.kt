package cryptography.asymmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.Security
import java.security.Signature

fun signer(privateKey: PrivateKey, message: ByteArray): ByteArray {
    //Add Bouncy Castle Provider
    Security.addProvider(BouncyCastleProvider())
    // RSA SSA - PSS 256
    val signature = Signature.getInstance("SHA256withRSAandMGF1", "BC")

    signature.initSign(privateKey, SecureRandom())
    signature.update(message)
    return signature.sign()
}