package uk.co.tomek.openweatherkt.presenter

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import uk.co.tomek.openweatherkt.BuildConfig
import uk.co.tomek.openweatherkt.MainActivity
import uk.co.tomek.openweatherkt.network.OpenWeatherNetworkService
import uk.co.tomek.openweatherkt.view.MainView
import java.net.UnknownHostException

/**
 * Presenter for the [MainActivity].
 */
class MainPresenter(private val view: MainView,
                    private val networkService: OpenWeatherNetworkService,
                    private val uiScheduler: Scheduler) {
    private val disposables = CompositeDisposable()

    init {
        disposables.add(onLondonSearch())
    }

    fun onDestroy() {
        disposables.clear()
    }

    private fun onLondonSearch(): Disposable {
        return view.onCitySelected()
                .flatMapSingle { location ->
                    Timber.v("Getting weather for location:%s", location)
                    networkService.getFiveDayForecast(location, BuildConfig.API_KEY)
                }
                .observeOn(uiScheduler)
                .doOnSubscribe { view.showProgress(true) }
                .doOnEach { view.showProgress(false) }
                .doOnError { throwable ->
                    if (throwable is HttpException || throwable is UnknownHostException) {
                        view.showNetworkError()
                    } else {
                        view.showError()
                    }
                }
                .retryWhen { view.onRetryClicked() }
                .subscribe({ weatherResponse ->
                    Timber.v("Response received %s", weatherResponse)
                    view.showFiveDayWeather(weatherResponse.list)
                }, { throwable ->
                    view.showError()
                    Timber.e(throwable, "Error")
                })
    }

}