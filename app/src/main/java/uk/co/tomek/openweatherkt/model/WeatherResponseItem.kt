package uk.co.tomek.openweatherkt.model

data class WeatherResponseItem(
        val dt: Long,
        val main: Main,
        val weather: List<Weather>,
        val clouds: Clouds?,
        val wind: Wind?,
        val rain: Rain?,
        val sys: Sys?,
        val dt_txt: String,
        val base: String?,
        val visibility: Int,
        val coord: Coord?,
        val id: Int,
        val name: String?,
        val cod: Int
)