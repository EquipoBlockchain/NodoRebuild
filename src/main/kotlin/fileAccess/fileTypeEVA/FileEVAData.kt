/**
 * Copyright 2021 Kyle Elbjorn
 *
 * This file is part of GEHIRN Node.
 *
 * GEHIRN Node is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * GEHIRN Node is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with GEHIRN Node.
 * If not, see <https://www.gnu.org/licenses/>.
 */

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