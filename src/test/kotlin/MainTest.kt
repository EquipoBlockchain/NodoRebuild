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

val kPGenInstance  = KeyPairGenerator()
val sKGenInstance  = SecretKeyGenerator()
val sKGenInstance2 = SecretKeyGenerator()
val sKEncInstance  = SecretKeyEncrypt()
val sKDecInstance  = SecretKeyDecrypt()
val fEVARInstance  = FileEVAReader()

fun app(password: CharArray, j: Int) {

    val message = getExampleMessage()

    kPGenInstance.generate()

    val publicKeyEncodedBA  = kPGenInstance.getPublicKeyX509Encoded()
    val privateKeyEncodedBA = kPGenInstance.getPrivateKeyPKCS8Encoded()

    val keyFactoryInstance  = KeyFactory.getInstance("RSA", "BC")

    val signResult = signer(
        encodedPrivateKey = privateKeyEncodedBA,
        message           = message
    )

    val isVerified = verifier(
        encodedPublicKey = publicKeyEncodedBA,
        message          = message,
        signature    = signResult
    )

    val salt = getNewSalt()

    // TODO Check for viability of adding pepper

    sKGenInstance.generate(password, salt)

    val initVectorBytes = getNewInitVectorBytes()

    sKEncInstance.encrypt(
        input          = publicKeyEncodedBA,
        secretKey      = sKGenInstance.getSecretKey(),
        initVectorBytes = initVectorBytes
    )

    val cipherPublicKeyEncodedBA     = sKEncInstance.cipherBytes
    val cipherPublicKeyEncodedBASize = sKEncInstance.cipherBytesLength

    sKEncInstance.encrypt(
        input          = privateKeyEncodedBA,
        secretKey      = sKGenInstance.getSecretKey(),
        initVectorBytes = initVectorBytes
    )

    val cipherPrivateKeyEncodedBA     = sKEncInstance.cipherBytes
    val cipherPrivateKeyEncodedBASize = sKEncInstance.cipherBytesLength

    val path     = "cages/"
    val fileName = "Pilot$j"
    val format   = "EVA00Prototype"

    val input    = salt + initVectorBytes + cipherPublicKeyEncodedBA + cipherPrivateKeyEncodedBA
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

    val saltFromEVA                         = byteArrayEVA.copyOfRange(0, 16)     //16
    val initVectorBytesFromEVA              = byteArrayEVA.copyOfRange(16, 32)    //16
    val cipherPublicKeyX509EncodedFromEVA   = byteArrayEVA.copyOfRange(32, 208)   //176
    val cipherPrivateKeyPKCS8EncodedFromEVA = byteArrayEVA.copyOfRange(208, 848)  //640

    sKGenInstance2.generate(password, saltFromEVA)

    sKDecInstance.decrypt(
        cipherBytes       = cipherPublicKeyX509EncodedFromEVA,
        cipherBytesLength = 176,
        secretKey         = sKGenInstance2.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    val decipherPublicKeyX509EncodedBA = sKDecInstance.plainBytes
    val decipherPublicKeyDecoded       = keyFactoryInstance.generatePublic(X509EncodedKeySpec(decipherPublicKeyX509EncodedBA))

    sKDecInstance.decrypt(
        cipherBytes       = cipherPrivateKeyPKCS8EncodedFromEVA,
        cipherBytesLength = 640,
        secretKey         = sKGenInstance2.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    val decipherPrivateKeyPKCS8EncodedBA = sKDecInstance.plainBytes
    val decipherPrivateKeyDecoded        = keyFactoryInstance.generatePrivate(PKCS8EncodedKeySpec(decipherPrivateKeyPKCS8EncodedBA))

    println("-------------------------------------START--------------------------------------------------------------")
    println("Verified                             : $isVerified")

    println("cipherPublicKeyEncodedBASize  is 176 : ${cipherPublicKeyEncodedBASize  == 176}")
    println("cipherPrivateKeyEncodedBASize is 640 : ${cipherPrivateKeyEncodedBASize == 640}")
    println("-------------------------------------END----------------------------------------------------------------")

}
