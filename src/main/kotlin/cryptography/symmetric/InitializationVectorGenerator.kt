package cryptography.symmetric

import javax.crypto.spec.IvParameterSpec

fun getNewInitVectorParamSpec(): IvParameterSpec {
    //TODO SecureRandom 16-byte IVBytes generator
    val provisionalIVBytes = byteArrayOf(
        0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00,
        0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00
    )

    return IvParameterSpec(provisionalIVBytes)
}