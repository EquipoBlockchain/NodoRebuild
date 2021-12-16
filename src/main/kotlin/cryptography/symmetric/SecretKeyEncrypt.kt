package cryptography.symmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class SecretKeyEncrypt {
    var cipherText: ByteArray? = null
    var cipherTextLength       = 0

    fun encrypt(
        input:          ByteArray,
        secretKey:      SecretKey,
        initVectorSpec: IvParameterSpec
    ) {
        //Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, initVectorSpec)

        cipherText       =  ByteArray(cipher.getOutputSize(input.size))
        cipherTextLength =  cipher.update(input, 0, input.size, cipherText, 0)
        cipherTextLength += cipher.doFinal(cipherText, cipherTextLength)
    }
}