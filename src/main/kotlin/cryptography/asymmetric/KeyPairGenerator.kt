package cryptography.asymmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Security
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

class KeyPairGenerator {
    private lateinit var keyPair: KeyPair

    fun getPublicKeyX509Encoded(): ByteArray {
        val encodedPublicKeyX509  = X509EncodedKeySpec(keyPair.public.encoded)
        return encodedPublicKeyX509.encoded
    }

    fun getPrivateKeyPKCS8Encoded(): ByteArray {
        val encodedPrivateKeyPKCS8 = PKCS8EncodedKeySpec(keyPair.private.encoded)
        return encodedPrivateKeyPKCS8.encoded
    }

    fun generate() {
        //Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val keyGen = KeyPairGenerator.getInstance("RSA", "BC")
        keyGen.initialize(1024, SecureRandom())
        keyPair    = keyGen.generateKeyPair()
    }
}