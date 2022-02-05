package fileAccess

// TODO subject to change

/**
 * Directory in the system, where the EVA file is stored (Expected to end with / character).
 *
 * @return [String]
 * */
fun getPath(): String {
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
fun getFormat(): String {
    return "eva"
}