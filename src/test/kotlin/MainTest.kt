import cryptography.asymmetric.KeyPairGenerator
import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import cryptography.symmetric.*
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

    for (j in 0 until 3) {
        app(password)
    }
}

val kPGenInstance = KeyPairGenerator()
val sKGenInstance = SecretKeyGenerator()
val sKEncInstance = SecretKeyEncrypt()
val sKDecInstance = SecretKeyDecrypt()

fun app(password: CharArray): Unit {
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

    sKEncInstance.encrypt(
        input          = publicKeyEncodedBA,
        secretKey      = sKGenInstance.getSecretKey()!!,
        initVectorSpec = iVSpec
    )

    val cipherPublicKeyEncodedBA     = sKEncInstance.cipherText
    val cipherPublicKeyEncodedBASize = sKEncInstance.cipherTextLength

    sKEncInstance.encrypt(
        input          = privateKeyEncodedBA,
        secretKey      = sKGenInstance.getSecretKey()!!,
        initVectorSpec = iVSpec
    )

    val cipherPrivateKeyEncodedBA     = sKEncInstance.cipherText
    val cipherPrivateKeyEncodedBASize = sKEncInstance.cipherTextLength

    writeToEVA(
        cipherPublicKeyEncodedBA!!,
        cipherPrivateKeyEncodedBA!!
    )

    sKDecInstance.decrypt(
        cipherText       = cipherPublicKeyEncodedBA!!,
        cipherTextLength = cipherPublicKeyEncodedBASize,
        secretKey        = sKGenInstance.getSecretKey()!!,
        initVectorSpec   = iVSpec
    )

    val decipherPublicKeyEncodedBA     = sKDecInstance.plainText

    sKDecInstance.decrypt(
        cipherText       = cipherPrivateKeyEncodedBA!!,
        cipherTextLength = cipherPrivateKeyEncodedBASize,
        secretKey        = sKGenInstance.getSecretKey()!!,
        initVectorSpec   = iVSpec
    )

    val decipherPrivateKeyEncodedBA     = sKDecInstance.plainText

    println("-------------------------------------START--------------------------------------------------------------")
    println("Verified                             : $isVerified")

    println("cipherPublicKeyEncodedBASize  is 176 : ${cipherPublicKeyEncodedBASize == 176}")
    println("cipherPrivateKeyEncodedBASize is 640 : ${cipherPrivateKeyEncodedBASize == 640}")

    println("Precipher matches Decipher (public)  : ${publicKeyEncodedBA.contentEquals(decipherPublicKeyEncodedBA)}")
    println("Precipher matches Decipher (private) : ${privateKeyEncodedBA.contentEquals(decipherPrivateKeyEncodedBA)}")
    println("-------------------------------------END----------------------------------------------------------------")
}

fun writeToEVA(
    encPublicKeyBA:  ByteArray,
    encPrivateKeyBA: ByteArray,
): Unit {
    try {
        val byteFile = File("Pilot00.eva")

        if (byteFile.createNewFile()) {
            println("El archivo no existe")
            val outputStream = FileOutputStream(byteFile)
            outputStream.write(encPublicKeyBA)
            outputStream.write(encPrivateKeyBA)
            outputStream.close()
            println("Ahora si")
        } else {
            println("El archivo existia")
            byteFile.delete()
            println("Ya no")
            byteFile.createNewFile()
            val outputStream = FileOutputStream(byteFile)
            outputStream.write(encPublicKeyBA)
            outputStream.write(encPrivateKeyBA)
            outputStream.close()
            println("Otravez existe")
        }
        println("Si se guardo perro")
    } catch (e: IOException) {
        println("No se guardo perro")
    }
}
