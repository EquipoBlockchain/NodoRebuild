package cryptography.asymmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.PublicKey
import java.security.Security
import java.security.Signature

fun verifier(publicKey: PublicKey, message: ByteArray, signByteArray: ByteArray): Boolean {
    //Add Bouncy Castle Provider
    Security.addProvider(BouncyCastleProvider())
    // RSA SSA - PSS 256
    val signature = Signature.getInstance("SHA256withRSAandMGF1", "BC")

    signature.initVerify(publicKey)
    signature.update(message)
    return signature.verify(signByteArray)
}