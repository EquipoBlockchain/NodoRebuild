import registry.user.addPadding

fun main() {
    val user1 = "JulioRegalado123".toByteArray(Charsets.UTF_8)
    val user2 = "JulioRegalado".toByteArray(Charsets.UTF_8)
    val user3 = "Julio".toByteArray(Charsets.UTF_8)

    val paddedUser1 = addPadding(user1)
    val paddedUser2 = addPadding(user2)
    val paddedUser3 = addPadding(user3)

    println("${toHex(paddedUser1)} bytes: ${paddedUser1.size}")
    println("${toHex(paddedUser2)} bytes: ${paddedUser2.size}")
    println("${toHex(paddedUser3)} bytes: ${paddedUser3.size}")
}