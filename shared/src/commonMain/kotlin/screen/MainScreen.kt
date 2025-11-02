package screen

import VOCApp
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object MainScreen : Screen {
    @Composable
    override fun Content() {
        VOCApp()
    }
}
