package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.facebook
import voctrust.shared.generated.resources.instagram
import voctrust.shared.generated.resources.x

@Composable
fun ImageCarousel() {
    val images = listOf(
        Res.drawable.instagram, // Replace with your image resources
        Res.drawable.x,
        Res.drawable.facebook
    )
    var currentPage by remember { mutableStateOf(0) }
    var isDragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    val dragThreshold = 100f

    // Auto-scroll with reset on interaction
    LaunchedEffect(currentPage) {
        while (true) {
            delay(3000)
            if (!isDragging) {
                currentPage = (currentPage + 1) % images.size
            }
        }
    }

    // Display content
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { isDragging = true },
                onDragEnd = {
                    isDragging = false
                    if (dragOffset > dragThreshold) {
                        currentPage = if (currentPage == 0) images.lastIndex else currentPage - 1
                    } else if (dragOffset < -dragThreshold) {
                        currentPage = (currentPage + 1) % images.size
                    }
                    dragOffset = 0f
                },
                onDragCancel = {
                    isDragging = false
                    dragOffset = 0f
                }
            ) { change, dragAmount ->
                change.consume()
                dragOffset += dragAmount.x
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(images[currentPage]),
                contentDescription = "Image $currentPage",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            images.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        .clickable {
                            currentPage = index
                        }
                )
            }
        }
    }
}