package navigation

import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import login.loginScreen
import login.options.loginOptionsScreen
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
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                //TODO
            }
            AppNavType.LIVE     -> {
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                //TODO
            }
            AppNavType.LOGIN    -> {
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                loginScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.REGISTRY -> {
                sideBarReturnToMenu(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                registryScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LOGIN_OPTIONS -> {
                sideBarCloseLoginSession(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                loginOptionsScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
            }
            AppNavType.LOGIN_VERIFICATIONS -> {
                sideBarReturnToLoginOptionsScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                //TODO
            }
            AppNavType.LOGIN_VOTING -> {
                sideBarReturnToLoginOptionsScreen(
                    colorPalette = colorPalette,
                    navItemState = navItemState
                )
                //TODO
            }
        }
    }
}