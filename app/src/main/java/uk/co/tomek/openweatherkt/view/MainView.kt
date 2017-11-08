package uk.co.tomek.openweatherkt.view

import io.reactivex.Observable
import uk.co.tomek.openweatherkt.model.WeatherResponseItem

/**
 * View interface for the main screen.
 */
interface MainView {

    fun showFiveDayWeather(weatherResponses: List<WeatherResponseItem>)

    fun showError()

    fun showNetworkError()

    fun showProgress(shouldShow: Boolean)

    fun onCitySelected(): Observable<String>

    fun onRetryClicked(): Observable<Boolean>
}