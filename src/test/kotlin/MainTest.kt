import cryptography.asymmetric.KeyPairGenerator
import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import cryptography.symmetric.*
import fileAccess.FileEVAReader
import fileAccess.createEVA
import java.io.*
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import kotlin.math.pow

fun main() {

    print("Input password : ")
    val reader        = BufferedReader(InputStreamReader(System.`in`))
    val passwordInput = reader.readLine()
    val password      = passwordInput.toCharArray()

    for (j in 0 until 5) {
        app(password, j)
    }
}

val kPGenInstance = KeyPairGenerator()
val sKGenInstance = SecretKeyGenerator()
val sKEncInstance = SecretKeyEncrypt()
val sKDecInstance = SecretKeyDecrypt()
val fEVARInstance = FileEVAReader()

fun app(password: CharArray, j: Int) {
    kPGenInstance.generate()

    val messageSize = 2.0.pow(10.0).toInt()
    //println("Message Size : $messageSize")
    val message = ByteArray(messageSize)
    for (i in 0 until messageSize) {
        message[i] = 'a'.code.toByte()
    }

    val publicKeyEncodedBA  = kPGenInstance.getPublicKeyX509Encoded()
    val privateKeyEncodedBA = kPGenInstance.getPrivateKeyPKCS8Encoded()

    val keyFactoryInstance  = KeyFactory.getInstance("RSA", "BC")

    // Private key recovered from PKCS8EncodedKeySpec
    val privateKeyPKCS8Enc  = PKCS8EncodedKeySpec(privateKeyEncodedBA)
    val recoveredPrivateKey = keyFactoryInstance.generatePrivate(privateKeyPKCS8Enc)

    val signResult = signer(
        privateKey = recoveredPrivateKey,
        message    = message
    )

    // Public key recovered from X509EncodedKeySpec
    val publicKeyX509Enc    = X509EncodedKeySpec(publicKeyEncodedBA)
    val recoveredPublicKey  = keyFactoryInstance.generatePublic(publicKeyX509Enc)

    val isVerified = verifier(
        publicKey     = recoveredPublicKey,
        message       = message,
        signByteArray = signResult
    )

    val salt = getNewSalt()

    sKGenInstance.generate(password, salt)

    val iVSpec = getNewInitVectorParamSpec()

    sKGenInstance.getSecretKey()?.let {
        sKEncInstance.encrypt(
            input          = publicKeyEncodedBA,
            secretKey      = it,
            initVectorSpec = iVSpec
        )
    }

    val cipherPublicKeyEncodedBA     = sKEncInstance.cipherText
    val cipherPublicKeyEncodedBASize = sKEncInstance.cipherTextLength

    sKGenInstance.getSecretKey()?.let {
        sKEncInstance.encrypt(
            input          = privateKeyEncodedBA,
            secretKey      = it,
            initVectorSpec = iVSpec
        )
    }

    val cipherPrivateKeyEncodedBA     = sKEncInstance.cipherText
    val cipherPrivateKeyEncodedBASize = sKEncInstance.cipherTextLength

    val path     = "Cages/"
    val fileName = "Pilot$j"
    val format   = "EVA00Prototype"

    val input    = cipherPublicKeyEncodedBA!! + cipherPrivateKeyEncodedBA!!
    // TODO Replace for Byte Array conjoin (Header + Keys) P.S. Watch out for the Non NULL

    val wasCreated = createEVA(
        path     = path,
        fileName = fileName,
        format   = format,
        input    = input
    )

    println("wasCreated? : $wasCreated")

    fEVARInstance.readEVA(
        path     = path,
        fileName = fileName,
        format   = format
    )

    val byteArrayEVA = fEVARInstance.fileEVAToByteArray

    val publicKeyFromEVA  = byteArrayEVA?.copyOfRange(0, 176)
    val privateKeyFromEVA = byteArrayEVA?.copyOfRange(176, 816)

    publicKeyFromEVA?.let {
        sKDecInstance.decrypt(
            cipherText       = it,
            cipherTextLength = cipherPublicKeyEncodedBASize,
            secretKey        = sKGenInstance.getSecretKey()!!,
            initVectorSpec   = iVSpec
        )
    }

    val decipherPublicKeyEncodedBA     = sKDecInstance.plainText

    privateKeyFromEVA?.let {
            sKDecInstance.decrypt(
                cipherText       = it,
                cipherTextLength = cipherPrivateKeyEncodedBASize,
                secretKey        = sKGenInstance.getSecretKey()!!,
                initVectorSpec   = iVSpec
            )
    }

    val decipherPrivateKeyEncodedBA     = sKDecInstance.plainText

    println("-------------------------------------START--------------------------------------------------------------")
    println("Verified                             : $isVerified")

    println("cipherPublicKeyEncodedBASize  is 176 : ${cipherPublicKeyEncodedBASize == 176}")
    println("cipherPrivateKeyEncodedBASize is 640 : ${cipherPrivateKeyEncodedBASize == 640}")

    println("Precipher matches Decipher (public)  : ${publicKeyEncodedBA.contentEquals(decipherPublicKeyEncodedBA)}")
    println("Precipher matches Decipher (private) : ${privateKeyEncodedBA.contentEquals(decipherPrivateKeyEncodedBA)}")
    println("-------------------------------------END----------------------------------------------------------------")
}
