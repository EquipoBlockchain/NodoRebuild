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

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import navigation.AppNavType
import navigation.bodyContent

fun main() = application {
    val icon = painterResource("Icons/temp-icon.png")

    Window(
        onCloseRequest = ::exitApplication,
        title          = "Sistema de Votación",
        icon           = icon,
        resizable      = false,
        //state          = WindowState(placement = WindowPlacement.Maximized)
        state          = WindowState(
            width     = 1270.dp,
            height    =  800.dp,
            placement = WindowPlacement.Floating,
            position  = WindowPosition(Alignment.Center)
        )
    ) {


        val colorPalette = Colors(
            primary          = Color(0xFF765898),//#e436c1
            primaryVariant   = Color(0xFF593d7b),//#e824e5
            secondary        = Color(0xFF52d053),//#fcff22
            secondaryVariant = Color(0xFF38be39),//#cfd32e
            background       = Color(0xFF000000),//#ffffff
            surface          = Color(0xFF000000),//#ffffff
            error            = Color(0xFFe6770b),//#B00020
            onPrimary        = Color(0xFFffffff),//#ffffff
            onSecondary      = Color(0xFF000000),//#000000
            onBackground     = Color(0xFFfafafa),//#2c2c2c
            onSurface        = Color(0xFFfafafa),//#2c2c2c
            onError          = Color(0xFF000000),//#ffffff
            isLight          = false
        ) // TEMPORAL SCHEME, CHANGE ON GRADLE
        // lightColors()
        // darkColors()

        app(colorPalette)
    }
}

@Composable
fun app(colorPalette: Colors) {
    val navItemState = remember { mutableStateOf(AppNavType.MENU) }
    bodyContent(colorPalette, navItemState , navItemState.value)
}




