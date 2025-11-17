package section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import component.ImageViewerDialog
import model.CloudinaryResource
import org.jetbrains.compose.resources.painterResource
import screen.GalleryScreen
import utils.CloudinaryApi
import utils.ImageLoaderProvider
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.logo_voc

@Composable
internal fun GallerySection(isMobile: Boolean) {
    var resourceList by remember { mutableStateOf<List<CloudinaryResource>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow
    val imagePreviewSize = if (isMobile) 4 else 6

    Column(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gallery",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        GalleryScreen(
            cloudinaryResourceList = resourceList,
            isLoading = isLoading,
            onLoadMore = { navigator.push(GalleryScreen) }
        )
    }

    LaunchedEffect(Unit) {
        isLoading = true
        val response = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.GALLERY)
        resourceList = response.resources.take(imagePreviewSize)
        isLoading = false
    }
}

@Composable
fun GalleryScreen(
    cloudinaryResourceList: List<CloudinaryResource>,
    isLoading: Boolean,
    onLoadMore: () -> Unit
) {
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

    if (isLoading && cloudinaryResourceList.isEmpty()) {
        // Initial loader
        Box(
            modifier = Modifier.fillMaxSize().heightIn(min = 600.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                cloudinaryResourceList.forEachIndexed { index, resource ->
                    GalleryImageItem(
                        cloudinaryResource = resource,
                        onClick = { selectedImageIndex = index }
                    )
                }
            }

            Button(onClick = onLoadMore, modifier = Modifier.padding(16.dp)) {
                Text("Load More")
            }
        }
    }
}

@Composable
fun GalleryImageItem(
    cloudinaryResource: CloudinaryResource,
    onClick: () -> Unit
) {
    AsyncImage(
        imageLoader = ImageLoaderProvider.getImageLoader(LocalPlatformContext.current),
        model = cloudinaryResource.thumbnailUrl ?: cloudinaryResource.previewUrl ?: cloudinaryResource.secureUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        onError = { error ->
            println(error)
        },
        modifier = Modifier
            .size(310.dp)
            .padding(5.dp)
            .clickable(onClick = onClick),
        placeholder = painterResource(Res.drawable.logo_voc)
    )
}
