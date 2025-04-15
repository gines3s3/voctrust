package model

data class Service(
    val title: String,
    val description: String,
    val imageUrl: String = "" // Optional field for future use
)