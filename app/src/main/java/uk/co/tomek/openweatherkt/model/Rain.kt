package uk.co.tomek.openweatherkt.model

import com.squareup.moshi.Json

class Rain(
        @Json(name = "3h")
        val threeh: Float
)