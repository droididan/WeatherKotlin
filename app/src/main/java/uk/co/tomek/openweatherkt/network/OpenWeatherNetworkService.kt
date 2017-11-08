package uk.co.tomek.openweatherkt.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.tomek.openweatherkt.model.WeatherResponse

/**
 * Retrofit service for open weather.
 */
interface OpenWeatherNetworkService {
    @GET("forecast?units=metric")
    fun getFiveDayForecast(@Query("q") cityNameAndCountry: String,
                                    @Query("appid") apiKey: String): Single<WeatherResponse>
}