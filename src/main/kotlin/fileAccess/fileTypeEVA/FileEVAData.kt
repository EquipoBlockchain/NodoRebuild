package fileAccess.fileTypeEVA

// TODO subject to change

/**
 * Directory in the system, where the EVA files are stored (Expected to end with / character).
 *
 * @return [String]
 * */
fun getEVAPath(): String {
    return "cages/"
}

/**
 * String is processed into an EVA file name.
 *
 * @return [String]
 * */
fun String.toEVAFileName(): String {
    return "Pilot$this"
}

/**
 * Designated extension of the EVA file.
 *
 * @return [String]
 * */
fun getEVAFormat(): String {
    return ".eva"
}