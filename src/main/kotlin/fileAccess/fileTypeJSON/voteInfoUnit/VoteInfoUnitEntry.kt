package fileAccess.fileTypeJSON.voteInfoUnit

data class VoteInfoUnitEntry(
    val blockNumber : Int,
    val description : String,
    val question    : String,
    val options     : MutableList<VoteInfoUnitEntryOptions>
)
