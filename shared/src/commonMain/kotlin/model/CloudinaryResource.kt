package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudinaryResource(
    @SerialName("asset_id") val assetId: String,
    @SerialName("public_id") val publicId: String,
    val format: String,
    val version: Long,
    @SerialName("resource_type") val resourceType: String,
    val type: String,
    @SerialName("created_at") val createdAt: String,
    val bytes: Int,
    val width: Int,
    val height: Int,
    @SerialName("asset_folder") val assetFolder: String,
    @SerialName("display_name") val displayName: String,
    val url: String,
    @SerialName("secure_url") val secureUrl: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("preview_url") val previewUrl: String? = null
)