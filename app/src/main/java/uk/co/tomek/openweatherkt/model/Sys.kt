package uk.co.tomek.openweatherkt.model

data class Sys(
        val type: Int,
        val id: Int,
        val message: Float,
        val sunrise: Long,
        val sunset: Long
)