package fileAccess.fileTypeJSON.nodeListMAGI

// TODO subject to change

/**
 * Directory in the system, where the MAGI nodes list JSON file is stored (Expected to end with / character).
 *
 * @return [String]
 * */
fun getJSONNodeListMAGIPath(): String {
    return "nodeInformation/"
}

/**
 * String is processed into an EVA file name.
 *
 * @return [String]
 * */
fun getJSONNodeListMAGIName(): String {
    return "nodeListMAGI"
}

/**
 * Designated extension of the EVA file.
 *
 * @return [String]
 * */
fun getJSONNodeListMAGIFormat(): String {
    return ".json"
}