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

package navigation

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import live.liveScreen
import login.loginScreen
import login.options.loginOptionsScreen
import login.voting.votingScreen
import mainMenu.menuScreen
import registry.registryScreen

@Composable
fun bodyContent(
    colorPalette : Colors,
    navItemState : MutableState<AppNavType>,
    appNavType   : AppNavType
) {
    Crossfade(targetState = appNavType) {
        when (it) {
            AppNavType.MENU     -> {
                menuScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.CHAIN    -> {
                //TODO
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LIVE     -> {
                liveScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LOGIN    -> {
                loginScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.REGISTRY -> {
                registryScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LOGIN_OPTIONS -> {
                loginOptionsScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                sideBarCloseLoginSession(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LOGIN_VERIFICATIONS -> {
                //TODO
                sideBarReturnToLoginOptionsScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LOGIN_VOTING -> {
                votingScreen(
                    colorPalette = colorPalette
                )
                sideBarReturnToLoginOptionsScreen(
                        colorPalette = colorPalette,
                navItemState = navItemState
                )
            }
        }
    }
}