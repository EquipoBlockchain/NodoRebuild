package fileAccess.fileTypeJSON.voteInfoUnit

// TODO subject to change

/**
 * Directory in the system, where the MAGI nodes list JSON file is stored (Expected to end with / character).
 *
 * @return [String]
 * */
fun getJSONVoteInfoUnitPath(): String {
    return "voteInformation/"
}

/**
 * String is processed into an EVA file name.
 *
 * @return [String]
 * */
fun Int.toJSONVoteInfoUnitName(): String {
    return "MAGI${this}VoteInfoUnit"
}

/**
 * Designated extension of the EVA file.
 *
 * @return [String]
 * */
fun getJSONVoteInfoUnitFormat(): String {
    return ".json"
}