package model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProduct(
    val id: Int = 0,
    val title: String = "",
    val price: Float = 0F,
    val description: String = "",
    val category: String = "",
    val image: String = "",
    val rating: Rating = Rating()
)

@Serializable
data class Rating(
    val rate: Float = 0F,
    val count: Int = 0,
)
