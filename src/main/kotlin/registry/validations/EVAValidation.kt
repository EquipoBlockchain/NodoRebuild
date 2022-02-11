package registry.validations

import cryptography.asymmetric.KeyPairGenerator
import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import cryptography.symmetric.*
import fileAccess.*
import fileAccess.fileTypeEVA.FileEVAReader
import fileAccess.fileTypeEVA.createEVA
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

private lateinit var publicKeyX509Encoded           : ByteArray
private lateinit var privateKeyPKCS8Encoded         : ByteArray
private lateinit var cipherPublicKeyX509Encoded     : ByteArray
private lateinit var cipherPrivateKeyPKCS8Encoded   : ByteArray
private lateinit var decipherPublicKeyX509Encoded   : ByteArray
private lateinit var decipherPrivateKeyPKCS8Encoded : ByteArray

/**
 * TODO Document
 * KotlinLogging Implemented.
 */
fun isEVAValid(
    file       : File,
    password   : CharArray,
    userPadded : ByteArray,
    id         : ByteArray
): Boolean {
    val keyPairGeneratorInstance = KeyPairGenerator()
    keyPairGeneratorInstance.generate()

    publicKeyX509Encoded   = keyPairGeneratorInstance.getPublicKeyX509Encoded()
    privateKeyPKCS8Encoded = keyPairGeneratorInstance.getPrivateKeyPKCS8Encoded()

    if (
        isKeyPairValid(
            publicKeyX509Encoded   = publicKeyX509Encoded,
            privateKeyPKCS8Encoded = privateKeyPKCS8Encoded
        )
    ) {
        logger.info { "Generated Key Pair is usable." }
    } else {
        logger.error { "Generated Key Pair is not usable." }
        return false
    }

    val salt            = getNewSalt()
    val initVectorBytes = getNewInitVectorBytes()

    if (
        isEncryptionValid(
            password          = password,
            salt              = salt,
            initVectorBytes   = initVectorBytes,
            publicKeyEncoded  = publicKeyX509Encoded,
            privateKeyEncoded = privateKeyPKCS8Encoded
        )
    ) {
        logger.info { "Encrypted key sizes match expected size." }
    } else {
        logger.error { "Encrypted key sizes do not match expected size." }
        return false
    }

    if (
        isEVACreationValid(
            file            = file,
            userPadded      = userPadded,
            id              = id,
            salt            = salt,
            initVectorBytes = initVectorBytes
        )
    ) {
        logger.info { "EVA creation correct." }
    } else {
        logger.error { "EVA creation encountered a problem." }
        return false
    }

    if (
        isEVADecryptionValid(
            file              = file,
            passwordCharArray = password,
            userByteArray     = userPadded,
            idByteArray       = id,
            salt              = salt,
            initVectorBytes   = initVectorBytes
        )
    ) {
        logger.info { "EVA decryption correct." }
    } else {
        logger.error { "EVA decryption encountered a problem." }
        return false
    }

    return true
}

/**
 * TODO Document
 * TODO Log
 */
fun isKeyPairValid(
    publicKeyX509Encoded   : ByteArray,
    privateKeyPKCS8Encoded : ByteArray
): Boolean {
    val message    = getExampleMessage()

    val signResult = signer(
        privateKeyPKCS8Encoded = privateKeyPKCS8Encoded,
        message                = message
    )

    return verifier(
        publicKeyX509Encoded = publicKeyX509Encoded,
        message              = message,
        signature            = signResult
    )
}

/**
 * TODO Document
 * TODO Log
 */
fun isEncryptionValid(
    password          : CharArray,
    salt              : ByteArray,
    initVectorBytes   : ByteArray,
    publicKeyEncoded  : ByteArray,
    privateKeyEncoded : ByteArray
): Boolean {
    val secretKeyGeneratorInstance = SecretKeyGenerator()

    secretKeyGeneratorInstance.generate(
        password = password,
        salt     = salt
    )

    val secretKeyEncryptInstance = SecretKeyEncrypt()

    secretKeyEncryptInstance.encrypt(
        input           = publicKeyEncoded,
        secretKey       = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes = initVectorBytes
    )

    cipherPublicKeyX509Encoded = secretKeyEncryptInstance.cipherBytes

    secretKeyEncryptInstance.encrypt(
        input           = privateKeyEncoded,
        secretKey       = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes = initVectorBytes
    )

    cipherPrivateKeyPKCS8Encoded = secretKeyEncryptInstance.cipherBytes

    if (
        cipherPublicKeyX509Encoded.size   == 176 &&
        cipherPrivateKeyPKCS8Encoded.size == 640
    ) {
        return true
    }
    return false
}

/**
 * TODO Document
 * TODO Log
 */
fun isEVACreationValid(
    file            : File,
    userPadded      : ByteArray,
    id              : ByteArray,
    salt            : ByteArray,
    initVectorBytes : ByteArray
): Boolean {

    if(
        createEVA(
            file                         = file,
            userPadded                   = userPadded,
            id                           = id,
            salt                         = salt,
            initVectorBytes              = initVectorBytes,
            cipherPublicKeyX509Encoded   = cipherPublicKeyX509Encoded,
            cipherPrivateKeyPKCS8Encoded = cipherPrivateKeyPKCS8Encoded
        )
    ) {
        return true
    }
    return false
}


/**
 * TODO Document
 * TODO Log
 * */
fun isEVADecryptionValid(
    file              : File,
    passwordCharArray : CharArray,
    userByteArray     : ByteArray,
    idByteArray       : ByteArray,
    salt              : ByteArray,
    initVectorBytes   : ByteArray
): Boolean {
    val fileEVAReaderInstance = FileEVAReader()
    fileEVAReaderInstance.readEVA(
        file = file,
    )

    val userPaddedByteArrayFromEVA          = fileEVAReaderInstance.userPadded
    val idFromEVA                           = fileEVAReaderInstance.id
    val saltFromEVA                         = fileEVAReaderInstance.salt
    val initVectorBytesFromEVA              = fileEVAReaderInstance.initVectorBytes
    val cipherPublicKeyX509EncodedFromEVA   = fileEVAReaderInstance.cipherPublicKeyX509Encoded
    val cipherPrivateKeyPKCS8EncodedFromEVA = fileEVAReaderInstance.cipherPrivateKeyPKCS8Encoded

    val userPaddedByteArray = addPadding(
        byteArray = userByteArray,
        size      = 16
    )

    val secretKeyGeneratorInstance = SecretKeyGenerator()
    secretKeyGeneratorInstance.generate(
        password = passwordCharArray,
        salt     = saltFromEVA
    )

    val secretKeyDecryptInstance = SecretKeyDecrypt()

    secretKeyDecryptInstance.decrypt(
        cipherBytes       = cipherPublicKeyX509EncodedFromEVA,
        cipherBytesLength = 176,
        secretKey         = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    decipherPublicKeyX509Encoded = secretKeyDecryptInstance.plainBytes

    secretKeyDecryptInstance.decrypt(
        cipherBytes       = cipherPrivateKeyPKCS8EncodedFromEVA,
        cipherBytesLength = 640,
        secretKey         = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    decipherPrivateKeyPKCS8Encoded = secretKeyDecryptInstance.plainBytes

    if (
        userPaddedByteArray.contentEquals(userPaddedByteArrayFromEVA)        &&
        idByteArray.contentEquals(idFromEVA)                                 &&
        salt.contentEquals(saltFromEVA)                                      &&
        initVectorBytes.contentEquals(initVectorBytesFromEVA)                &&
        publicKeyX509Encoded.contentEquals(decipherPublicKeyX509Encoded)     &&
        privateKeyPKCS8Encoded.contentEquals(decipherPrivateKeyPKCS8Encoded)
    ){
        return true
    }
    return false
}