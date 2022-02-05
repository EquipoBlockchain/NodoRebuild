package base64

import cryptography.asymmetric.KeyPairGenerator
import org.bouncycastle.util.encoders.Base64

fun main() {
    val keyPairGeneratorInstance = KeyPairGenerator()
    keyPairGeneratorInstance.generate()
    println("PublicKeyX509EncodedBase64  : ${keyPairGeneratorInstance.getPublicKeyX509Encoded().toBase64()}")
    println("PrivateKeyPKCS8EncodedBase64: ${keyPairGeneratorInstance.getPrivateKeyPKCS8Encoded().toBase64()}")
}

fun ByteArray.toBase64(): String {
    return Base64.encode(this).toString(Charsets.UTF_8)
}
