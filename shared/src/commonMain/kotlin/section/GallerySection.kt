package section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import component.AnimatedSection
import component.Footer
import component.ImageViewerDialog
import kotlinx.coroutines.launch
import model.CloudinaryResource
import org.jetbrains.compose.resources.painterResource
import utils.CloudinaryApi
import utils.ImageLoaderProvider
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.logo_voc

@Composable
internal fun GallerySection(
    visible: Boolean,
) = AnimatedSection(
    visible,
    modifier = Modifier.padding(32.dp),
) {
    var resourceList by remember { mutableStateOf<List<CloudinaryResource>>(emptyList()) }
    var nextCursor by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    GalleryScreen(
        cloudinaryResourceList = resourceList,
        onLoadMore = {
            if (nextCursor != null && !isLoading) {
                isLoading = true
                kotlinx.coroutines.MainScope().launch {
                    val response =
                        CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.GALLERY, nextCursor)
                    resourceList = resourceList + response.resources
                    nextCursor = response.nextCursor
                    isLoading = false
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        isLoading = true
        val response = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.GALLERY)
        resourceList = response.resources
        nextCursor = response.nextCursor
        isLoading = false
    }

    Footer()
}

@Composable
fun GalleryScreen(
    cloudinaryResourceList: List<CloudinaryResource>,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyGridState()
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    // Dialog state handling
    if (selectedImageIndex != null) {
        val imageUrls = cloudinaryResourceList.map { it.secureUrl }
        ImageViewerDialog(
            imageUrls = imageUrls,
            initialIndex = selectedImageIndex!!,
            onDismiss = { selectedImageIndex = null }
        )
    }

    LaunchedEffect(cloudinaryResourceList.size) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null) {
                    val lastIndex = lastVisibleItem.index
                    val total = cloudinaryResourceList.size
                    if (lastIndex >= total - 5) {
                        onLoadMore()
                    }
                }
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 310.dp),
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
    ) {
        items(cloudinaryResourceList.withIndex().toList()) { (index, resource) ->
            GalleryImageItem(
                cloudinaryResource = resource,
                onClick = { selectedImageIndex = index }
            )
        }
    }
}

@Composable
fun GalleryImageItem(cloudinaryResource: CloudinaryResource,
                     onClick: () -> Unit) {
    AsyncImage(
        imageLoader = ImageLoaderProvider.getImageLoader(LocalPlatformContext.current),
        model = cloudinaryResource.thumbnailUrl ?: cloudinaryResource.previewUrl
        ?: cloudinaryResource.secureUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        onError = { error ->
            println(error)
        },
        modifier = Modifier
            .size(310.dp)
            .padding(5.dp)// Fixed size for all thumbnails
            .clickable(onClick = onClick),
        placeholder = painterResource(Res.drawable.logo_voc)
    )
}
