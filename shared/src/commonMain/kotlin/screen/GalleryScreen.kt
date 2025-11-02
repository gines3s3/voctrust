package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import page.FullGalleryPage

object GalleryScreen : Screen {
    @Composable
    override fun Content() {
        FullGalleryPage()
    }
}
