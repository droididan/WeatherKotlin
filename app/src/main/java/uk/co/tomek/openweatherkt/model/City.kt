package uk.co.tomek.openweatherkt.model

data class City(
        val id: Int,
        val name: String,
        val coord: Coord,
        val country: String
)