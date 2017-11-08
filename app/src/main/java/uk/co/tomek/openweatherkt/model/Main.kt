package uk.co.tomek.openweatherkt.model

data class Main(
        val temp: Float,
        val pressure: Float,
        val humidity: Int,
        val temp_min: Float,
        val temp_max: Float
)