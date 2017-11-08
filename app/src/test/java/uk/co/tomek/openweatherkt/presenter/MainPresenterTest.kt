package uk.co.tomek.openweatherkt.presenter

import com.jakewharton.rxrelay2.PublishRelay
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import uk.co.tomek.openweatherkt.model.City
import uk.co.tomek.openweatherkt.model.Coord
import uk.co.tomek.openweatherkt.model.Main
import uk.co.tomek.openweatherkt.model.WeatherResponse
import uk.co.tomek.openweatherkt.model.WeatherResponseItem
import uk.co.tomek.openweatherkt.network.OpenWeatherNetworkService
import uk.co.tomek.openweatherkt.view.MainView

/**
 * Test for [MainPresenter].
 */
class MainPresenterTest {

    private val onCitySelectedObservable = PublishRelay.create<String>()
    private val onWeatherResponseObservable = PublishRelay.create<WeatherResponse>()
    private val onRetryObservable = PublishRelay.create<Boolean>()
    private val networkService: OpenWeatherNetworkService = mock()
    private val scheduler = Schedulers.trampoline()
    private lateinit var testView: MainView
    private lateinit var testPresenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testView = createView()
        testPresenter = createPresenter()
        whenever(networkService.getFiveDayForecast(any(), any())).thenReturn(onWeatherResponseObservable.firstOrError())
    }

    private fun createPresenter(): MainPresenter {
        return MainPresenter(testView, networkService, scheduler)
    }

    private fun createView(): MainView {
        return mock {
            on { onCitySelected() } doReturn onCitySelectedObservable
            on { onRetryClicked() } doReturn onRetryObservable
        }
    }

    @Test
    fun weatherLocationSelected_showResults() {
        // Given
        val searchTerm = "London,UK"
        val weatherItem = WeatherResponseItem(1L, Main(1f, 2f,1,1f,2f), emptyList(),
                null, null, null, null, "dfs", "base", 1, null, 1, null, 1)
        val weatherList = listOf(weatherItem)
        val city = City(1, "cityName", Coord(0f, 0f), "UK")
        val netResponse = WeatherResponse(1, 1f, 1, weatherList, city)

        // When
        onCitySelectedObservable.accept(searchTerm)
        onWeatherResponseObservable.accept(netResponse)

        // Then
        verify(testView).showProgress(true)
        verify(testView).showFiveDayWeather(weatherList)
    }
}