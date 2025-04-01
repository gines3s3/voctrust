package component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import kotlinx.coroutines.delay
import utils.CloudinaryApi
import utils.ImageLoaderProvider

@Composable
fun ImageCarousel() {

    var mediaUrls by remember { mutableStateOf<List<String>>(emptyList()) }
    var currentPage by remember { mutableStateOf(0) }
    var isDragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    val dragThreshold = 100f
    val autoScrollDelay = 5000L


    // Fetch media from Cloudinary
    LaunchedEffect(Unit) {
        mediaUrls = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.CAROUSEL).map { it.secureUrl }
    }

    // Auto-scroll with reset on interaction
    LaunchedEffect(currentPage) {
        while (true) {
            delay(autoScrollDelay)
            if (!isDragging) {
                currentPage = (currentPage + 1) % mediaUrls.size
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
                        currentPage = if (currentPage == 0) mediaUrls.lastIndex else currentPage - 1
                    } else if (dragOffset < -dragThreshold) {
                        currentPage = (currentPage + 1) % mediaUrls.size
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
                .height(500.dp)
        ) {
            if (mediaUrls.isNotEmpty()) {
                val currentMediaUrl = mediaUrls[currentPage % mediaUrls.size]

                var isLoading by remember { mutableStateOf(true) }
                var isError by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        imageLoader = ImageLoaderProvider.getImageLoader(LocalPlatformContext.current),
                        model = currentMediaUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        onLoading = { isLoading = true },
                        onSuccess = { isLoading = false },
                        onError = { error ->
                            println(error)
                            isLoading = false;
                            isError = true
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        mediaUrls.forEachIndexed { index, _ ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (index == currentPage) MaterialTheme.colorScheme.surface
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                    )
                                    .clickable {
                                        currentPage = index
                                    }
                            )
                        }
                    }

                    // Loading state
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    // Error state
                    if (isError) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.errorContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(MaterialTheme.colorScheme.error, CircleShape)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Failed to load image",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}