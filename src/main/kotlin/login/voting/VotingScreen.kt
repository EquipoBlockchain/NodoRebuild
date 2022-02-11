package login.voting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * TODO Document
 *
 */
@Composable
fun votingScreen(
    colorPalette: Colors
) {
    MaterialTheme(
        colors = colorPalette
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(pattern)
                //TODO bit activation
                if(pattern != "Unstable REI Pattern") {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(blockNumber.toString())

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(description)

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(question)

                    Spacer(modifier = Modifier.height(20.dp))

                    options.forEach { option ->
                        Text("Option #${option.optionNumber}: ${option.optionDefinition}")
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
