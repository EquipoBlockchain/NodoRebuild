import cryptography.symmetric.getNewInitVectorBytes
import cryptography.symmetric.getNewSalt

fun main() {
    for (j in 0 until 10) {
        println(toHex(getNewSalt()))
        println(toHex(getNewInitVectorBytes()))
    }
}