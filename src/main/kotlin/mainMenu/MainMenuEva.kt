package mainMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import navigation.AppNavType

@Composable
fun menuEVAScreen(navItemState: MutableState<AppNavType>) {

    val magiColors = Colors(
        primary          = Color(0xFF000000),//#000000
        primaryVariant   = Color(0xFF121212),//#121212
        secondary        = Color(0xFF18B96A),//#18B96A
        secondaryVariant = Color(0xFF149C58),//#149C58
        background       = Color(0xFF000000),//#000000
        surface          = Color(0xFF000000),//#000000
        error            = Color(0xFFB00020),//#B00020
        onPrimary        = Color(0xFFE37A2D),//#E37A2D
        onSecondary      = Color(0xFF000000),//#000000
        onBackground     = Color(0xFFFFFF00),//#FFFF00
        onSurface        = Color(0xFF00B0F0),//#00B0F0
        onError          = Color(0xFFffffff),//#ffffff
        isLight          = false
    )

    MaterialTheme(
        colors = magiColors
    ) {

        // Back
        val back = painterResource("MagiVectors/Back.svg")
        Box(
            modifier         = Modifier
                .fillMaxSize()
                .background(color = magiColors.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter            = back,
                contentDescription = "Back"
            )
        }

        // MAGI
        val magi1 = painterResource("MagiVectors/MAGI1.svg")
        val magi2 = painterResource("MagiVectors/MAGI2.svg")
        val magi3 = painterResource("MagiVectors/MAGI3.svg")
        Box(
            modifier         = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            val fromTop13  = 460.dp
            val fromTop2   =  40.dp
            val fromCenter = 640.dp
            val width13    = 490.dp
            val width2     = 385.dp

            // MAGI 1
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    fromTop13,
                    fromCenter,
                    0.dp
                )
            ) {
                Image(
                    painter            = magi1,
                    contentDescription = "MAGI 1",
                    modifier           = Modifier
                        .width(width13)
                        .clickable {
                            navItemState.value = AppNavType.CHAIN
                        }
                )
            }

            // MAGI 1
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    fromTop2,
                    0.dp,
                    0.dp
                )
            ) {
                Image(
                    painter            = magi2,
                    contentDescription = "MAGI 2",
                    modifier           = Modifier
                        .width(width2)
                        .clickable {
                            navItemState.value = AppNavType.LIVE
                        }
                )
            }

            // MAGI 3
            Column(
                modifier = Modifier.padding(
                    fromCenter,
                    fromTop13,
                    0.dp,
                    0.dp
                )
            ) {
                Image(
                    painter            = magi3,
                    contentDescription = "MAGI 3",
                    modifier           = Modifier
                        .width(width13)
                        .clickable {
                            navItemState.value = AppNavType.LOGIN
                        }
                )
            }
        }

    }
}