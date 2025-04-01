package utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import model.CloudinaryResource

object CloudinaryApi {

    private fun getBaseUrl(workerName: String) = "https://$workerName.gines-thilla.workers.dev"

    private val httpClient = HttpClient()

    suspend fun fetchMedia(mediaType: MediaType): List<CloudinaryResource> {
        return try {
            val responseText = httpClient.get(getBaseUrl(mediaType.workerName)).body<String>()
            val jsonResponse = Json.parseToJsonElement(responseText).jsonObject
            val resources = jsonResponse["resources"]?.jsonArray ?: return emptyList()
            resources.mapNotNull {
                try {
                    Json.decodeFromJsonElement<CloudinaryResource>(it)
                } catch (e: Exception) {
                    println("1" + e.message)
                    null
                }
            }
        } catch (e: Exception) {
            println("2" + e.message)
            emptyList()
        }
    }

    enum class MediaType(val workerName: String) {
        CAROUSEL("voc-carousel"),
        GALLERY("voc-gallery")
        // VIDEO
    }
}
