    package utils

    import io.ktor.client.HttpClient
    import io.ktor.client.call.body
    import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
    import io.ktor.client.request.get
    import io.ktor.client.request.parameter
    import io.ktor.client.request.url
    import io.ktor.serialization.kotlinx.json.json
    import kotlinx.serialization.json.Json
    import model.ResourceResponse

    object CloudinaryApi {


        private val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }

        suspend fun fetchMedia(mediaType: MediaType, nextCursor: String? = null): ResourceResponse {
            return try {
                httpClient.get {
                    url(getBaseUrl(mediaType.workerName))
                    nextCursor?.let { cursor ->
                        parameter("next_cursor", cursor)
                    }
                }.body()
            } catch (e: Exception) {
                println("Error fetching media: ${e.message}")
                ResourceResponse(emptyList(), null)
            }
        }

        private fun getBaseUrl(workerName: String) = "https://$workerName.gines-thilla.workers.dev"

        enum class MediaType(val workerName: String) {
            CAROUSEL("voc-carousel"),
            GALLERY("voc-gallery")
            // VIDEO
        }
    }
