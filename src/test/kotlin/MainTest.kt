import cryptography.asymmetric.KeyPairGenerator
import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import cryptography.symmetric.*
import fileAccess.FileEVAReader
import fileAccess.createEVA
import fileAccess.getExampleMessage
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

fun main() {
    val path     = "cages/"
    val folder = File(path)
    println("cages emptied?: ${folder.deleteRecursively()}")

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

    val message = getExampleMessage()

    kPGenInstance.generate()

    val publicKeyEncodedBA  = kPGenInstance.getPublicKeyX509Encoded()
    val privateKeyEncodedBA = kPGenInstance.getPrivateKeyPKCS8Encoded()

    val keyFactoryInstance  = KeyFactory.getInstance("RSA", "BC")

    // Private key recovered from PKCS8EncodedKeySpec
    val privateKeyPKCS8Encoded = PKCS8EncodedKeySpec(privateKeyEncodedBA)
    val recoveredPrivateKey    = keyFactoryInstance.generatePrivate(privateKeyPKCS8Encoded)

    val signResult = signer(
        privateKey = recoveredPrivateKey,
        message    = message
    )

    // Public key recovered from X509EncodedKeySpec
    val publicKeyX509Encoded = X509EncodedKeySpec(publicKeyEncodedBA)
    val recoveredPublicKey   = keyFactoryInstance.generatePublic(publicKeyX509Encoded)

    val isVerified = verifier(
        publicKey     = recoveredPublicKey,
        message       = message,
        signByteArray = signResult
    )

    val salt = getNewSalt()

    sKGenInstance.generate(password, salt)

    val iVSpec = getNewInitVectorParamSpec()

    sKEncInstance.encrypt(
        input          = publicKeyX509Encoded.encoded,
        secretKey      = sKGenInstance.getSecretKey(),
        initVectorSpec = iVSpec
    )

    val cipherPublicKeyEncodedBA     = sKEncInstance.cipherText
    val cipherPublicKeyEncodedBASize = sKEncInstance.cipherTextLength

    sKEncInstance.encrypt(
        input          = privateKeyPKCS8Encoded.encoded,
        secretKey      = sKGenInstance.getSecretKey(),
        initVectorSpec = iVSpec
    )

    val cipherPrivateKeyEncodedBA     = sKEncInstance.cipherText
    val cipherPrivateKeyEncodedBASize = sKEncInstance.cipherTextLength

    val path     = "cages/"
    val fileName = "Pilot$j"
    val format   = "EVA00Prototype"

    val input    = cipherPublicKeyEncodedBA + cipherPrivateKeyEncodedBA
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

    val cipherPublicKeyX509EncodedFromEVA   = byteArrayEVA.copyOfRange(0, 176)
    val cipherPrivateKeyPKCS8EncodedFromEVA = byteArrayEVA.copyOfRange(176, 816)

    sKDecInstance.decrypt(
        cipherText       = cipherPublicKeyX509EncodedFromEVA,
        cipherTextLength = 176,
        secretKey        = sKGenInstance.getSecretKey(),
        initVectorSpec   = iVSpec
    )

    val decipherPublicKeyX509EncodedBA = sKDecInstance.plainText
    val decipherPublicKeyDecoded       = keyFactoryInstance.generatePublic(X509EncodedKeySpec(decipherPublicKeyX509EncodedBA))

    sKDecInstance.decrypt(
        cipherText       = cipherPrivateKeyPKCS8EncodedFromEVA,
        cipherTextLength = 640,
        secretKey        = sKGenInstance.getSecretKey(),
        initVectorSpec   = iVSpec
    )

    val decipherPrivateKeyPKCS8EncodedBA = sKDecInstance.plainText
    val decipherPrivateKeyDecoded        = keyFactoryInstance.generatePrivate(PKCS8EncodedKeySpec(decipherPrivateKeyPKCS8EncodedBA))

    println("-------------------------------------START--------------------------------------------------------------")
    println("Verified                             : $isVerified")

    println("cipherPublicKeyEncodedBASize  is 176 : ${cipherPublicKeyEncodedBASize  == 176}")
    println("cipherPrivateKeyEncodedBASize is 640 : ${cipherPrivateKeyEncodedBASize == 640}")

    println("Recovered matches Decipher-Decoded (public)  : ${recoveredPublicKey.equals(decipherPublicKeyDecoded)}")
    println("Recovered matches Decipher-Decoded (private) : ${recoveredPrivateKey.equals(decipherPrivateKeyDecoded)}")
    println("-------------------------------------END----------------------------------------------------------------")
}
