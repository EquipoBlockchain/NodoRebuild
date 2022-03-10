import fileAccess.addPadding

fun main() {
    val user1 = "JulioRegalado123".toByteArray(Charsets.UTF_8)
    val user2 = "JulioRegalado".toByteArray(Charsets.UTF_8)
    val user3 = "Julio".toByteArray(Charsets.UTF_8)

    val paddedUser1 = user1.addPadding(16)
    val paddedUser2 = user2.addPadding(16)
    val paddedUser3 = user3.addPadding(16)

    println("${toHex(paddedUser1)} bytes: ${paddedUser1.size}")
    println("${toHex(paddedUser2)} bytes: ${paddedUser2.size}")
    println("${toHex(paddedUser3)} bytes: ${paddedUser3.size}")
}