package page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import component.ImageViewerDialog
import kotlinx.coroutines.flow.filter
import model.CloudinaryResource
import org.jetbrains.compose.resources.painterResource
import utils.CloudinaryApi
import utils.ImageLoaderProvider
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.logo_voc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullGalleryPage() {
    var resourceList by remember { mutableStateOf<List<CloudinaryResource>>(emptyList()) }
    var nextCursor by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }
    val listState = rememberLazyGridState()
    val navigator = LocalNavigator.currentOrThrow

    // Dialog state handling for the full gallery
    if (selectedImageIndex != null) {
        val imageUrls = resourceList.map { it.secureUrl }
        ImageViewerDialog(
            imageUrls = imageUrls,
            initialIndex = selectedImageIndex!!,
            onDismiss = { selectedImageIndex = null }
        )
    }

    // Infinite scroll
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filter { it != null && it >= resourceList.size - 5 && !isLoading && nextCursor != null }
            .collect {
                isLoading = true
                val response = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.GALLERY, nextCursor)
                resourceList = resourceList + response.resources
                nextCursor = response.nextCursor
                isLoading = false
            }
    }

    // Initial data fetch
    LaunchedEffect(Unit) {
        isLoading = true
        val response = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.GALLERY)
        resourceList = response.resources
        nextCursor = response.nextCursor
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gallery") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        if (isLoading && resourceList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Adaptive(minSize = 310.dp),
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                itemsIndexed(resourceList) { index, resource ->
                    GalleryImageItem(
                        cloudinaryResource = resource,
                        onClick = { selectedImageIndex = index }
                    )
                }

                if (isLoading) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GalleryImageItem(cloudinaryResource: CloudinaryResource, onClick: () -> Unit) {
    AsyncImage(
        imageLoader = ImageLoaderProvider.getImageLoader(LocalPlatformContext.current),
        model = cloudinaryResource.thumbnailUrl ?: cloudinaryResource.previewUrl ?: cloudinaryResource.secureUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(310.dp)
            .padding(5.dp)
            .clickable(onClick = onClick),
        placeholder = painterResource(Res.drawable.logo_voc)
    )
}
