package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.LucideIcons
import com.composables.icons.lucide.X

@Composable
fun ImageViewerDialog(
    imageUrls: List<String>,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Close button at top right
            IconCircleButton(
                icon = LucideIcons.X,
                contentDescription = "Close",
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left arrow area
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.15f),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentIndex > 0) {
                        IconCircleButton(
                            icon = LucideIcons.ArrowLeft,
                            contentDescription = "Previous",
                            onClick = { currentIndex-- }
                        )
                    }
                }

                // Image area
                Box(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                ) {
                    AsyncImage(
                        model = imageUrls[currentIndex],
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Right arrow area
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.15f),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentIndex < imageUrls.size - 1) {
                        IconCircleButton(
                            icon = LucideIcons.ArrowRight,
                            contentDescription = "Next",
                            onClick = { currentIndex++ }
                        )
                    }
                }
            }
        }
    }
}
