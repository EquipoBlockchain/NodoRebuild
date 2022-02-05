package network

data class Information (
    val originID    : UInt,
    val blockNumber : UInt,
    val description : String,
    val question    : String,
    val options     : Array<String>
) {
    fun isVoteInfoEqual(
        other : Any?
    ): Boolean {
        if (this === other)
            return true
        if (javaClass != other?.javaClass)
            return false

        other as Information

        if (blockNumber != other.blockNumber)
            return false
        if (description != other.description)
            return false
        if (question    != other.question)
            return false
        if (!options.contentEquals(other.options))
            return false

        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Information

        if (originID != other.originID) return false
        if (blockNumber != other.blockNumber) return false
        if (description != other.description) return false
        if (question != other.question) return false
        if (!options.contentEquals(other.options)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = originID.hashCode()
        result = 31 * result + blockNumber.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + question.hashCode()
        result = 31 * result + options.contentHashCode()
        return result
    }
}

data class VoteInfoUnit (
    val information : Information,
    val signature   : ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VoteInfoUnit

        if (information != other.information) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = information.hashCode()
        result = 31 * result + signature.contentHashCode()
        return result
    }
}
