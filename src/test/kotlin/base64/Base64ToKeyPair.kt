package base64

import cryptography.asymmetric.signer
import cryptography.asymmetric.verifier
import org.bouncycastle.util.encoders.Base64

// MAGI 1
const val voteInfoUnitString_1 =
    "\"originID\":10001,\"blockNumber\":1,\"description\":\"String\",\"question\":\"¿Quién debe conducir el EVA?\"," +
    "\"options\":{\"option01\":\"Rei\",\"option02\":\"Shinji\",\"option03\":\"Milzort\"}"

const val preCalculatedPublicKeyX509EncodedBase64_1 =
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCn6jXRYOGKQZytKp9shFdFGDm5nKhprtqRfyplwH0Y/KYSlUyaaf2ilQimCa9+FLNSanAy" +
    "FijGULpK1UD44FEi7xdxhO7mHOwClv0IlAup6c0W9Z31fl/MSqx6gksNcNplIgh9QJCNm+FU4nMeB1sFB6Wtxj9sGAS7CVN72f+uoQIDAQAB"

const val preCalculatedPrivateKeyPKCS8EncodedBase64_1 =
    "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKfqNdFg4YpBnK0qn2yEV0UYObmcqGmu2pF/KmXAfRj8phKVTJpp/aKVCKYJ" +
    "r34Us1JqcDIWKMZQukrVQPjgUSLvF3GE7uYc7AKW/QiUC6npzRb1nfV+X8xKrHqCSw1w2mUiCH1AkI2b4VTicx4HWwUHpa3GP2wYBLsJU3vZ" +
    "/66hAgMBAAECgYAniU8IsL4KNq60W0/UVkjSHQUifg9tat1N2tpZwPvzkTFtOLgjt4cf6+VwauFmV2ttoWAveuY447MpgZLdxrtzk6KMP3g7" +
    "qaaBzMVZM9OF9Y9uTIQvvS0Oos3IAJOL1jL0tpoTNZGTgfdIekjuAEvRjG0eN25gY/9jIwyJGK9j0QJBANEibCCT8njSQ88kvu4HcKVCG/QA" +
    "wfuHrtdxbJ0bsJ83qBbNHnDjK7qJzDiWhXdkNUjjjApp7J6tXg2gTPoaR60CQQDNixzRGDmA3FJYCuM4FElPEjDw8x3SbgI2MHCdxcst/t0z" +
    "3UIuzssVvI85RnwsU+JFqDDjSq0KW6QloNUJQ3FFAkBPk4KDLuhfUtqcckX4OnqBV1NdmAA33lwH/E+wxD2Dh+D8tBX2LGqSMyazDYtBb67a" +
    "ykKaH836Xstf8fP4YpGNAkEAyzNaO8StJJ2rsmqhE1HK36bmR26x7tTezt+4leFy6nfqC1oUEd5Sm5ycI01xeQk8ywxpuGqdA35xmtmTguLv" +
    "2QJBAIv34/ECqZ91Yhk53+Ue+TQtV+v9/qo1voCcHNUSiyueZGxMn6mWZS2xS7rB34Uzr0YvFG0QWnON3K6XUyc3tvs="

const val preCalculatedSignatureBase64_1 =
    "BUGaTdo0aOfrSLf/YQG1HAIIUI1CaZz908xV3oVLANQYXWZbsHeqqJPHMCLtOa64H8GLT50Lywza3XqI5HpHKV/4QyIQvN7GjfUzLNyn3awQ" +
    "LIlDIhHADLDhLxahgJFUKwuOUpvuHwMj80+fxUHeJ3+d49PTMMt/PmQhK6/C5Ug="

// MAGI 2
const val voteInfoUnitString_2 =
    "\"originID\":10002,\"blockNumber\":1,\"description\":\"String\",\"question\":\"¿Quién debe conducir el EVA?\"," +
    "\"options\":{\"option01\":\"Rei\",\"option02\":\"Shinji\",\"option03\":\"Milzort\"}"

const val preCalculatedPublicKeyX509EncodedBase64_2 =
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTIXW31L+z+CNcpHuLqPA01QjCTaix7nHF0mHIbsbwKCdrK+YUs/e1N2RMDEvPOw7457ap" +
    "3AxzAnxrK+Dd3iQimGkOz8Sk84ieYgWCup4P01j8QFfnI17R6EpPJs09YGeArGNAi+t+9NQ/YGMMHhe8iKxqFjejyeIybRP02GqKGQIDAQAB"

const val preCalculatedPrivateKeyPKCS8EncodedBase64_2 =
    "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJMhdbfUv7P4I1yke4uo8DTVCMJNqLHuccXSYchuxvAoJ2sr5hSz97U3ZEwM" +
    "S887DvjntqncDHMCfGsr4N3eJCKYaQ7PxKTziJ5iBYK6ng/TWPxAV+cjXtHoSk8mzT1gZ4CsY0CL63701D9gYwweF7yIrGoWN6PJ4jJtE/TY" +
    "aooZAgMBAAECgYABdv/Y46Ec3HYFRNsK4DsupOp0f75B3/jrbi0oq/Os/9p277kP70kLdbUh1qiAcbcxg/5F967R7Xm3+z0V2V5kTAnGUD2f" +
    "2qjoprafsza40UyBaH4Y8pp6EMc8RlxW1MKuCwdhpHCcHw32JiwNCMMotvwCjEH2KKL0k3zhmQd45QJBAMgKC/pd/IFjR70NUBD2nhig+f/D" +
    "W4kAU/VYxt4AxEUW2wqPniw93xvOm2rVckQang+ptrfthLZDVYbgY9sVoPUCQQC8SlR8ZxhzSlQCzOLDUgTTjInQwJz7xDwdoUoDXzx2XJ+0" +
    "T+iUQVlXijIzqToxa8/bh603odbGwUc3En5aZT4VAkBnuT3ovl81wALstkBz60yivKWkxulpqm/eYgAMER20LVu6qCl+Cz7qrkDMLo0nXx3i" +
    "RT/nMxODZnqsiyF78kUZAkAWiftFDP5DRsF5uFbotJe5TxhsMbJY/OuJTHZUowDU1k+TwQEBRjVQok1SG87IfbRa/gnBIBFap2ZD9GXFolrl" +
    "AkAOOR8QSWj/kTySqljzJjDhOOtVprGonmq7KnVKxeV8sz59Yr7LwbzPsCIzE7vwUJRh0ESfmQDR9YvFXB50ocHU"

const val preCalculatedSignatureBase64_2 =
    "RkdNtsgyZjzDlpiv6FYRKIvwLSe9fQloDaPiGBaSl78LPwcLAO7lMwOXbChgbeLj8ouUztxw/v/fyhYT2B3pkJnOug5MqQlv2ui4s9otDPNP" +
    "VpGvEtUBeVuP95n6Tuyow8S2+yZ6go/uh6wm8jRVNxiPrawi6Nv0qvzdW2FQHwc="

// MAGI 3
const val voteInfoUnitString_3 =
    "\"originID\":10003,\"blockNumber\":1,\"description\":\"String\",\"question\":\"¿Quién debe conducir el EVA?\"," +
    "\"options\":{\"option01\":\"Rei\",\"option02\":\"Shinji\",\"option03\":\"Milzort\"}"

const val preCalculatedPublicKeyX509EncodedBase64_3 =
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDR9ynF2UnBOxCPS5+teIKdrhyLGD28fmF6HnOUyBTCisXeu4eDRlvVceNw7usUuu3OfA/+" +
    "ZVSAxn4Y6emf6MtTpQNcVfWA4wJ5jXCajVFyNEOVHKdn67o8TgIr5WrrvSB7X2QcoAph0/YMDeZbWEu1lWkKtkeztcbuQqh9irhRCQIDAQAB"

const val preCalculatedPrivateKeyPKCS8EncodedBase64_3 =
    "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANH3KcXZScE7EI9Ln614gp2uHIsYPbx+YXoec5TIFMKKxd67h4NGW9Vx43Du" +
    "6xS67c58D/5lVIDGfhjp6Z/oy1OlA1xV9YDjAnmNcJqNUXI0Q5Ucp2frujxOAivlauu9IHtfZBygCmHT9gwN5ltYS7WVaQq2R7O1xu5CqH2K" +
    "uFEJAgMBAAECgYAIlzDZv0LGqcPLFeoDlY53V+jwQl6l3q0R6248ZFWzgmAHW+uqEe/h8y+K4fH/8buXGvmNWNhoDtCYwY4YeweoL0PrAupa" +
    "je+6SfNHzxDZ7N9DMteNvXHEAwubTWa+/PKalU+imuynZBR5rFXV+h+SHE9aabj83nF1nVwEGA25TQJBAPIUFcdO5yiSR9k4FAYOXXq/v++a" +
    "t2/Z0nnfqANUVpxv/eqBAdFiMftv1Tp55n3RGnDP3u1KJlEQQQEK8YvvL9MCQQDeCk5dGpfKhu7sTeVqAOZ3S+EK84ssZSwOj+6Bj5XdCdIc" +
    "UzdvC5ARvoJc2wC+YHZg2r+42wLcHkwQQNGgUs4zAkAuTScpOu89tFTnuDEkQ2Uf0EMu2gHR8/Cs/NlO4be7LjR7wPcPDGAQ/n/jMmOilvce" +
    "srOp4S/Y+2njgCtZH4OfAkAegXP6vqiUHq28sYcPZUhEo5wrkQJR+yRua7/s9Pk2AR9o3NYE36XPikkG0N/tR/t7mxI0J4LhExiIMx+Z00aZ" +
    "AkEAvrRdrisELgxI5s7+Us2ozqSvRQD9Y1IGMEPlA0LPV8StkSwii0OYjtBVFIkTqQrFg/482HsAvDXuZahbwmUCaA=="

const val preCalculatedSignatureBase64_3 =
    "adoSTcMnrldL4W6dqr5080nlLQANZ7jZeu/+gQTmZvZf9Q8DxUl0NWU6AA3tibGXebp1SrahX2av2T1/VX4ZG6nDtA7V++mwV2Ead23++K2j" +
    "OVpVOBPUurDqYQ1oE/yQPykmBz2VmfL4RJzGUF0cmq8Cwoq00HbEcjOVCzTqfB0="

fun main() {
    if (
        verifiedSigning(
            voteInfoUnitString      = voteInfoUnitString_1,
            publicKeyX509Encoded    = preCalculatedPublicKeyX509EncodedBase64_1.fromBase64ToByteArray(),
            privateKeyPKCS8Encoded  = preCalculatedPrivateKeyPKCS8EncodedBase64_1.fromBase64ToByteArray(),
            signature               = preCalculatedSignatureBase64_1.fromBase64ToByteArray()
        )
    ) {
        println("Key Pair 1 Verified.")
    }

    if (
        verifiedSigning(
            voteInfoUnitString      = voteInfoUnitString_2,
            publicKeyX509Encoded    = preCalculatedPublicKeyX509EncodedBase64_2.fromBase64ToByteArray(),
            privateKeyPKCS8Encoded  = preCalculatedPrivateKeyPKCS8EncodedBase64_2.fromBase64ToByteArray(),
            signature               = preCalculatedSignatureBase64_2.fromBase64ToByteArray()
        )
    ) {
        println("Key Pair 2 Verified.")
    }

    if (
        verifiedSigning(
            voteInfoUnitString      = voteInfoUnitString_3,
            publicKeyX509Encoded    = preCalculatedPublicKeyX509EncodedBase64_3.fromBase64ToByteArray(),
            privateKeyPKCS8Encoded  = preCalculatedPrivateKeyPKCS8EncodedBase64_3.fromBase64ToByteArray(),
            signature               = preCalculatedSignatureBase64_3.fromBase64ToByteArray()
        )
    ) {
        println("Key Pair 3 Verified.")
    }
}

fun String.fromBase64ToByteArray(): ByteArray {
    return Base64.decode(this.toByteArray(Charsets.UTF_8))
}

fun verifiedSigning(
    voteInfoUnitString     : String,
    publicKeyX509Encoded   : ByteArray,
    privateKeyPKCS8Encoded : ByteArray,
    signature              : ByteArray
): Boolean {

    val message    = voteInfoUnitString.toByteArray()

    /*
    val signResult = signer(
        privateKeyPKCS8Encoded = privateKeyPKCS8Encoded,
        message                = message
    )

    println(Base64.encode(signResult).toString(Charsets.UTF_8))
    */

    return verifier(
        publicKeyX509Encoded = publicKeyX509Encoded,
        message              = message,
        signature            = signature
    )
}