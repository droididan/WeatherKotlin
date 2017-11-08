package uk.co.tomek.openweatherkt.model

data class WeatherResponse(
        val cod: Int,
        val message: Float,
        val cnt: Int,
        val list: List<WeatherResponseItem>,
        val city: City
)