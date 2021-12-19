package cryptography.symmetric

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class SecretKeyDecrypt {
    private lateinit var paddedPlainText : ByteArray
    lateinit var plainText               : ByteArray
    var plainTextLength                  = 0

    fun decrypt(
        cipherText:       ByteArray,
        cipherTextLength: Int,
        secretKey:        SecretKey,
        initVectorSpec:   IvParameterSpec
    ) {
        //Add Bouncy Castle Provider
        Security.addProvider(BouncyCastleProvider())

        val decipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")

        decipher.init(Cipher.DECRYPT_MODE, secretKey, initVectorSpec)

        paddedPlainText =  ByteArray(decipher.getOutputSize(cipherTextLength))
        plainTextLength =  decipher.update(cipherText, 0, cipherTextLength, paddedPlainText, 0)
        plainTextLength += decipher.doFinal(paddedPlainText, plainTextLength)
        plainText       = paddedPlainText.copyOfRange(0, plainTextLength)
    }
}