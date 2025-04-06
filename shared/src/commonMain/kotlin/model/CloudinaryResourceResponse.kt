package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourceResponse(
    val resources: List<CloudinaryResource> = listOf(),
    @SerialName("total_count")
    val totalCount: Int? = null,
    @SerialName("next_cursor")
    val nextCursor: String? = null
)