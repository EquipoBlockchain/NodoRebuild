package fileAccess.fileTypeJSON.nodeListMAGI

data class NodeInfoMAGI(
    val identifier                 : Int,
    val address                    : String,
    val publicKeyX509EncodedBase64 : String
)
