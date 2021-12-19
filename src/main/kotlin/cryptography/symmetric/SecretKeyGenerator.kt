package cryptography.symmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class SecretKeyGenerator {
    private val iterations    = 4096
    private lateinit var sKey : SecretKey

    fun getSecretKey(): SecretKey {
        return sKey
    }

    fun generate(
        password: CharArray,
        salt:     ByteArray
    ) {
        //Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val keyFact = SecretKeyFactory.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC", "BC")
        val pbeSpec = PBEKeySpec(password, salt, iterations)
        sKey = keyFact.generateSecret(pbeSpec)
    }
}