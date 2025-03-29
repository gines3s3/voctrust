import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import utils.buildContentPath

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow(
            "VOC Youth Social Welfare Trust",
            canvasElementId = "VOCCanvas",
        ) {
            VOCApp(
                current = buildContentPath(),
            )
        }
    }
}
