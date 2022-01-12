import androidx.compose.animation.Crossfade
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import login.loginScreen
import mainMenu.menuScreen
import navigation.AppNavType
import navigation.sideBarReturnToMenu
import registry.registryScreen

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
            primary          = Color(0xFFe436c1),//#e436c1
            primaryVariant   = Color(0xFFe824e5),//#e824e5
            secondary        = Color(0xFFfcff22),//#fcff22
            secondaryVariant = Color(0xFFcfd32e),//#cfd32e
            background       = Color(0xFFffffff),//#ffffff
            surface          = Color(0xFFffffff),//#ffffff
            error            = Color(0xFFB00020),//#B00020
            onPrimary        = Color(0xFFffffff),//#ffffff
            onSecondary      = Color(0xFF000000),//#000000
            onBackground     = Color(0xFF2c2c2c),//#2c2c2c
            onSurface        = Color(0xFF2c2c2c),//#2c2c2c
            onError          = Color(0xFFffffff),//#ffffff
            isLight          = true
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

@Composable
fun bodyContent(
    colorPalette: Colors,
    navItemState: MutableState<AppNavType>,
    appNavType:   AppNavType
) {
    Crossfade(targetState = appNavType) {
        when (it) {
            AppNavType.MENU     -> {
                menuScreen(colorPalette, navItemState)
            }
            AppNavType.CHAIN     -> {
                sideBarReturnToMenu(colorPalette, navItemState)
            }
            AppNavType.LIVE     -> {
                sideBarReturnToMenu(colorPalette, navItemState)
            }
            AppNavType.LOGIN    -> {
                sideBarReturnToMenu(colorPalette, navItemState)
                loginScreen(colorPalette, navItemState)
            }
            AppNavType.REGISTRY -> {
                sideBarReturnToMenu(colorPalette, navItemState)
                registryScreen(colorPalette, navItemState)
            }
        }
    }
}



