import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import config.LocalAppConfigState
import config.rememberAppConfigState
import kotlinx.browser.window
import screen.GalleryScreen
import screen.MainScreen
import theme.rememberTypography

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(viewportContainerId = "ComposeTarget") {
        val useDarkTheme = isSystemInDarkTheme()
        val config = rememberAppConfigState(useDarkTheme)
        val typography = rememberTypography()

        CompositionLocalProvider(
            LocalAppConfigState provides config,
        ) {
            MaterialTheme(
                colorScheme = config.theme.schema,
                typography = typography,
            ) {
                // The Navigator manages the back stack and screen transitions
                Navigator(if (window.location.pathname == "/gallery") GalleryScreen else MainScreen) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}
