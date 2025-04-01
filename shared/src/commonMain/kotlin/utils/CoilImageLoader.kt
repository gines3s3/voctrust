package utils

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Provides a singleton ImageLoader with optimized configuration.
 */
object ImageLoaderProvider {
    private var instance: ImageLoader? = null

    /**
     * Returns a singleton ImageLoader instance with optimized settings.
     *
     * @param context The platform context used to create the ImageLoader
     * @param debug Whether to enable debug logging
     */
    fun getImageLoader(
        context: PlatformContext,
        debug: Boolean = false
    ): ImageLoader {
        return instance ?: createImageLoader(context, debug).also { instance = it }
    }

    private fun createImageLoader(context: PlatformContext, debug: Boolean): ImageLoader {
        return ImageLoader.Builder(context)
            // Add required components
            .components {
                add(KtorNetworkFetcherFactory())
            }
            // Configure memory cache
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25) // Use 25% of available memory
                    .build()
            }
            // Configure cache policies
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            // Configure coroutine contexts for better performance
            .fetcherCoroutineContext(EmptyCoroutineContext)
            .decoderCoroutineContext(EmptyCoroutineContext)
            // Add logging for debug builds
            .apply {
                if (debug) {
                    logger(DebugLogger())
                }
            }
            .build()
    }
}