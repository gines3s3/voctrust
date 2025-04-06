    package utils

    import io.ktor.client.HttpClient
    import io.ktor.client.call.body
    import io.ktor.client.request.get
    import kotlinx.serialization.json.Json
    import model.ResourceResponse

    object CloudinaryApi {


        private val httpClient = HttpClient()

        suspend fun fetchMedia(mediaType: MediaType, nextCursor: String? = null): ResourceResponse {
            var resourceResponse = ResourceResponse(emptyList(), null)
            return try {
                val baseUrl = getBaseUrl(mediaType.workerName)
                val url = if (nextCursor != null) "$baseUrl?next_cursor=$nextCursor" else baseUrl
                val responseText = httpClient.get(url).body<String>()
                try {
                    resourceResponse = Json.decodeFromString<ResourceResponse>(responseText)
                } catch (e: Exception) {
                    println("1 " + e.message)
                }
                resourceResponse
            } catch (e: Exception) {
                println("2 " + e.message)
                resourceResponse
            }
        }

        private fun getBaseUrl(workerName: String) = "https://$workerName.gines-thilla.workers.dev"

        enum class MediaType(val workerName: String) {
            CAROUSEL("voc-carousel"),
            GALLERY("voc-gallery")
            // VIDEO
        }
    }
