package registry.process

import cryptography.asymmetric.KeyPairGenerator
import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import cryptography.symmetric.*
import fileAccess.*

lateinit var publicKeyX509EncodedByteArray  : ByteArray
lateinit var privateKeyPKCS8EncodedByteArray : ByteArray

lateinit var cipherPublicKeyX509EncodedByteArray   : ByteArray
lateinit var cipherPrivateKeyPKCS8EncodedByteArray : ByteArray

lateinit var decipherPublicKeyX509EncodedByteArray   : ByteArray
lateinit var decipherPrivateKeyPKCS8EncodedByteArray : ByteArray

fun isEVAValid(
    path                : String,
    fileName            : String,
    format              : String,
    passwordCharArray   : CharArray,
    userPaddedByteArray : ByteArray,
    idByteArray         : ByteArray
    // TODO add mutable state for failure info
): Boolean {

    val keyPairGeneratorInstance    = KeyPairGenerator()
    keyPairGeneratorInstance.generate()
    publicKeyX509EncodedByteArray   = keyPairGeneratorInstance.getPublicKeyX509Encoded()
    privateKeyPKCS8EncodedByteArray = keyPairGeneratorInstance.getPrivateKeyPKCS8Encoded()

    if (
        !isKeyPairValid(
            publicKeyEncodedBA  = publicKeyX509EncodedByteArray,
            privateKeyEncodedBA = privateKeyPKCS8EncodedByteArray
        )
    ) {
        // TODO Info on mutable state
        return false
    }

    val salt            = getNewSalt()
    val initVectorBytes = getNewInitVectorBytes()

    if (
        !isEncryptionValid(
            passwordCharArray   = passwordCharArray,
            salt                = salt,
            initVectorBytes     = initVectorBytes,
            publicKeyEncodedBA  = publicKeyX509EncodedByteArray,
            privateKeyEncodedBA = privateKeyPKCS8EncodedByteArray
        )
    ) {
        // TODO Info on mutable state
        return false
    }

    if (
        !isEVACreationValid(
            path            = path,
            fileName        = fileName,
            format          = format,
            userByteArray   = userPaddedByteArray,
            idByteArray     = idByteArray,
            salt            = salt,
            initVectorBytes = initVectorBytes
        )
    ) {
        // TODO Info on mutable state
        return false
    }

    if (
        !isEVADecryptionValid(
            path              = path,
            fileName          = fileName,
            format            = format,
            passwordCharArray = passwordCharArray,
            userByteArray     = userPaddedByteArray,
            idByteArray       = idByteArray,
            salt              = salt,
            initVectorBytes   = initVectorBytes
        )
    ) {
        // TODO Info on mutable state
        return false
    }

    return true
}

fun isKeyPairValid(
    publicKeyEncodedBA  : ByteArray,
    privateKeyEncodedBA : ByteArray
): Boolean {

    val message    = getExampleMessage()

    val signResult = signer(
        encodedPrivateKey = privateKeyEncodedBA,
        message           = message
    )

    if (
        verifier(
            encodedPublicKey = publicKeyEncodedBA,
            message          = message,
            signature        = signResult
        )
    ) {
        return true
    }
    return false
}

fun isEncryptionValid(
    passwordCharArray   : CharArray,
    salt                : ByteArray,
    initVectorBytes     : ByteArray,
    publicKeyEncodedBA  : ByteArray,
    privateKeyEncodedBA : ByteArray
): Boolean {
    val secretKeyGeneratorInstance = SecretKeyGenerator()

    secretKeyGeneratorInstance.generate(
        password = passwordCharArray,
        salt     = salt
    )

    val secretKeyEncryptInstance = SecretKeyEncrypt()

    secretKeyEncryptInstance.encrypt(
        input           = publicKeyEncodedBA,
        secretKey       = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes = initVectorBytes
    )

    cipherPublicKeyX509EncodedByteArray = secretKeyEncryptInstance.cipherBytes

    secretKeyEncryptInstance.encrypt(
        input           = privateKeyEncodedBA,
        secretKey       = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes = initVectorBytes
    )

    cipherPrivateKeyPKCS8EncodedByteArray = secretKeyEncryptInstance.cipherBytes

    if (
        cipherPublicKeyX509EncodedByteArray.size  == 176 &&
        cipherPrivateKeyPKCS8EncodedByteArray.size == 640
    ) {
        return true
    }
    return false
}

fun isEVACreationValid(
    path            : String,
    fileName        : String,
    format          : String,
    userByteArray   : ByteArray,
    idByteArray     : ByteArray,
    salt            : ByteArray,
    initVectorBytes : ByteArray
): Boolean {

    if(
        createEVA(
            path                         = path,
            fileName                     = fileName,
            format                       = format,
            user                         = userByteArray,
            id                           = idByteArray,
            salt                         = salt,
            initVectorBytes              = initVectorBytes,
            cipherPublicKeyX509Encoded   = cipherPublicKeyX509EncodedByteArray,
            cipherPrivateKeyPKCS8Encoded = cipherPrivateKeyPKCS8EncodedByteArray
        )
    ) {
        return true
    }
    return false
}

fun isEVADecryptionValid(
    path              : String,
    fileName          : String,
    format            : String,
    passwordCharArray : CharArray,
    userByteArray     : ByteArray,
    idByteArray       : ByteArray,
    salt              : ByteArray,
    initVectorBytes   : ByteArray
): Boolean {
    val fileEVAReaderInstance = FileEVAReader()
    fileEVAReaderInstance.readEVA(
        path     = path,
        fileName = fileName,
        format   = format
    )

    val userFromEVA                         = fileEVAReaderInstance.user
    val idFromEVA                           = fileEVAReaderInstance.id
    val saltFromEVA                         = fileEVAReaderInstance.salt
    val initVectorBytesFromEVA              = fileEVAReaderInstance.initVectorBytes
    val cipherPublicKeyX509EncodedFromEVA   = fileEVAReaderInstance.cipherPublicKeyX509Encoded
    val cipherPrivateKeyPKCS8EncodedFromEVA = fileEVAReaderInstance.cipherPrivateKeyPKCS8Encoded

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

    decipherPublicKeyX509EncodedByteArray = secretKeyDecryptInstance.plainBytes

    secretKeyDecryptInstance.decrypt(
        cipherBytes       = cipherPrivateKeyPKCS8EncodedFromEVA,
        cipherBytesLength = 640,
        secretKey         = secretKeyGeneratorInstance.getSecretKey(),
        initVectorBytes   = initVectorBytesFromEVA
    )

    decipherPrivateKeyPKCS8EncodedByteArray = secretKeyDecryptInstance.plainBytes

    if (
        userByteArray.contentEquals(userFromEVA)                                               &&
        idByteArray.contentEquals(idFromEVA)                                                   &&
        salt.contentEquals(saltFromEVA)                                                        &&
        initVectorBytes.contentEquals(initVectorBytesFromEVA)                                  &&
        publicKeyX509EncodedByteArray.contentEquals(decipherPublicKeyX509EncodedByteArray)     &&
        privateKeyPKCS8EncodedByteArray.contentEquals(decipherPrivateKeyPKCS8EncodedByteArray)
    ){
        return true
    }
    return false
}