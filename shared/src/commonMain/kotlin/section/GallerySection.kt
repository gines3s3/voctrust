package section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import component.AnimatedSection
import component.Footer
import utils.CloudinaryApi
import utils.ImageLoaderProvider

@Composable
internal fun GallerySection(
    visible: Boolean,
) = AnimatedSection(
    visible,
    modifier = Modifier.padding(32.dp),
) {

    var mediaUrls by remember { mutableStateOf<List<GalleryImage>>(emptyList()) }

        GalleryScreen(mediaUrls)

    LaunchedEffect(Unit) {
        mediaUrls = CloudinaryApi.fetchMedia(CloudinaryApi.MediaType.GALLERY).map {
            GalleryImage(
                id = it.assetId,
                width = it.width,
                height = it.height,
                resource = it.secureUrl
            )
        }
    }

    Footer()
}


// Sample data class for images
data class GalleryImage(val id: String, val width: Int, val height: Int, val resource: String)

@Composable
fun GalleryScreen(images: List<GalleryImage>) {
    val sortedImages =
        images.sortedWith(compareBy({ it.height / it.width.toFloat() }, { it.width }))

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp), modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 600.dp)) {
        items(sortedImages) { image ->
            GalleryImageItem(image)
        }
    }
}

@Composable
fun GalleryImageItem(image: GalleryImage) {
    val aspectRatio = image.width.toFloat() / image.height.toFloat()

    //Box(modifier = Modifier.padding(4.dp)) {
        AsyncImage(
            imageLoader = ImageLoaderProvider.getImageLoader(LocalPlatformContext.current),
            model = image.resource,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onError = { error ->
                println(error)
            },
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .fillMaxWidth()
        )
    //}
}
