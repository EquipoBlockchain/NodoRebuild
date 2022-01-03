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

fun main() {
    val path   = "prototypeCages/"
    val folder = File(path)
    println("prototypeCages emptied?: ${folder.deleteRecursively()}")

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

    //val keyFactoryInstance  = KeyFactory.getInstance("RSA", "BC")

    val signResult = signer(
        encodedPrivateKey = privateKeyEncodedBA,
        message           = message
    )

    val isVerified = verifier(
        encodedPublicKey = publicKeyEncodedBA,
        message          = message,
        signature        = signResult
    )

    val user          = "Usuario123456789" //16 bytes
    val userByteArray = user.toByteArray(Charsets.UTF_8)

    val id            = "ABCD1234"
    val idByteArray   = id.toByteArray(Charsets.UTF_8)

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

    val path     = "prototypeCages/"
    val fileName = "Pilot$j"
    val format   = "EVA00Prototype"

    val wasCreated = createEVA(
        path                         = path,
        fileName                     = fileName,
        format                       = format,
        user                         = userByteArray,
        id                           = idByteArray,
        salt                         = salt,
        initVectorBytes              = initVectorBytes,
        cipherPublicKeyX509Encoded   = cipherPublicKeyEncodedBA,
        cipherPrivateKeyPKCS8Encoded = cipherPrivateKeyEncodedBA
    )

    println("wasCreated? : $wasCreated")

    fEVARInstance.readEVA(
        path     = path,
        fileName = fileName,
        format   = format
    )

    val userFromEVA                         = fEVARInstance.user
    val idFromEVA                           = fEVARInstance.id
    val saltFromEVA                         = fEVARInstance.salt
    val initVectorBytesFromEVA              = fEVARInstance.initVectorBytes
    val cipherPublicKeyX509EncodedFromEVA   = fEVARInstance.cipherPublicKeyX509Encoded
    val cipherPrivateKeyPKCS8EncodedFromEVA = fEVARInstance.cipherPrivateKeyPKCS8Encoded

    sKGenInstance2.generate(password, saltFromEVA)

    sKDecInstance.decrypt(
        cipherBytes       = cipherPublicKeyX509EncodedFromEVA,
        cipherBytesLength = 176,
        secretKey         = sKGenInstance2.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    val decipherPublicKeyX509EncodedBA = sKDecInstance.plainBytes
    //val decipherPublicKeyDecoded       = keyFactoryInstance.generatePublic(X509EncodedKeySpec(decipherPublicKeyX509EncodedBA))

    sKDecInstance.decrypt(
        cipherBytes       = cipherPrivateKeyPKCS8EncodedFromEVA,
        cipherBytesLength = 640,
        secretKey         = sKGenInstance2.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    val decipherPrivateKeyPKCS8EncodedBA = sKDecInstance.plainBytes
    //val decipherPrivateKeyDecoded        = keyFactoryInstance.generatePrivate(PKCS8EncodedKeySpec(decipherPrivateKeyPKCS8EncodedBA))

    println("-------------------------------------START--------------------------------------------------------------")
    println("Verified                             : $isVerified")

    println("User : ${userFromEVA.toString(Charsets.UTF_8)}")
    println("ID   : ${idFromEVA.toString(Charsets.UTF_8)}")

    println("cipherPublicKeyEncodedBASize  is 176 : ${cipherPublicKeyEncodedBASize  == 176}")
    println("cipherPrivateKeyEncodedBASize is 640 : ${cipherPrivateKeyEncodedBASize == 640}")
    println("is publicKeyEncodedBA  = decipherPublicKeyX509EncodedBA   : ${publicKeyEncodedBA.contentEquals(decipherPublicKeyX509EncodedBA)}")
    println("is privateKeyEncodedBA = decipherPrivateKeyPKCS8EncodedBA : ${privateKeyEncodedBA.contentEquals(decipherPrivateKeyPKCS8EncodedBA)}")
    println("-------------------------------------END----------------------------------------------------------------")

}
