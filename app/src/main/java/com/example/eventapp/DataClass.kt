package com.example.eventapp
@Serializable
data class DataClass (
    val datetimeLocal: String,
    val datetimeUtc: String,
    val id: Int,
    val performers: List<Performer>,
    val score: Double,
    val shortTitle: String,
    val stats: Stats,
    val taxonomies: List<Taxonomy>,
    val title: String,
    val type: String,
    val url: String,
    val venue: Venue
)  {
    data class Performer(
        val id: Int,
        val image: String,
        val images: Images,
        val name: String,
        val primary: Boolean,
        val score: Int,
        val shortName: String,
        val slug: String,
        val type: String,
        val url: String
    ) {
        data class Images(
            val huge: String,
            val large: String,
            val medium: String,
            val small: String
        )
    }

    data class Stats(
        val averagePrice: Int,
        val highestPrice: Int,
        val listingCount: Int,
        val lowestPrice: Int
    )

    data class Taxonomy(
        val id: Int,
        val name: String,
        val parentId: Any
    )

    data class Venue(
        val address: Any,
        val city: String,
        val country: String,
        val extendedAddress: Any,
        val id: Int,
        val location: Location,
        val name: String,
        val postalCode: String,
        val score: Double,
        val state: String,
        val url: String
    ) {
        data class Location(
            val lat: Double,
            val lon: Double
        )
    }
}