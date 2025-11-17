package component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import kotlinx.coroutines.delay
import model.CloudinaryResource
import utils.CloudinaryApi
import utils.ImageLoaderProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(isMobile: Boolean) {
    var mediaResources by remember { mutableStateOf<List<CloudinaryResource>>(emptyList()) }
    val pagerState = rememberPagerState { mediaResources.size }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // Fetch media from Cloudinary
    LaunchedEffect(Unit) {
        mediaResources = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.CAROUSEL).resources
    }

    // Auto-scroll that resumes after manual interaction
    LaunchedEffect(pagerState.pageCount) {
        if (pagerState.pageCount > 0) {
            while (true) {
                delay(5000L)
                // Only advance if the user is not interacting with the pager
                if (!isDragged) {
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }

    if (mediaResources.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth().height(500.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val currentMedia = mediaResources[page]
                val imageUrl = if (isMobile) {
                    currentMedia.thumbnailUrl ?: currentMedia.previewUrl ?: currentMedia.secureUrl
                } else {
                    currentMedia.secureUrl
                }

                var isLoading by remember { mutableStateOf(true) }
                var isError by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        imageLoader = ImageLoaderProvider.getImageLoader(LocalPlatformContext.current),
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        onState = { state ->
                            isLoading = state is coil3.compose.AsyncImagePainter.State.Loading
                            isError = state is coil3.compose.AsyncImagePainter.State.Error
                        }
                    )

                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    if (isError) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.errorContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Box(modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.error, CircleShape))
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            ) {
                mediaResources.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage) MaterialTheme.colorScheme.surface
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                            .clickable { /* Pager handles this */ }
                    )
                }
            }
        }
    }
}
