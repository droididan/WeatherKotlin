package uk.co.tomek.openweatherkt

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.github.ajalt.timberkt.Timber
import com.jakewharton.rxbinding2.widget.RxAdapterView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import uk.co.tomek.openweatherkt.adapter.WeatherResultsAdapter
import uk.co.tomek.openweatherkt.model.WeatherResponseItem
import uk.co.tomek.openweatherkt.network.OpenWeatherNetworkService
import uk.co.tomek.openweatherkt.presenter.MainPresenter
import uk.co.tomek.openweatherkt.view.MainView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var networkService: OpenWeatherNetworkService

    @BindView(R.id.progress_image)
    lateinit var progressImage: ImageView

    @BindView(R.id.city_selector_spinner)
    lateinit var citySelectorSpinner: Spinner

    @BindView(R.id.results_recycler_view)
    lateinit var resultsRecyclerView: RecyclerView

    private val resultsAdapter = WeatherResultsAdapter()
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        (this.applicationContext as OpenWeatherApp).mainComponent.inject(this)

        presenter = MainPresenter(this, networkService, AndroidSchedulers.mainThread())

        // Setup city selector spinner
        val adapter = ArrayAdapter
                .createFromResource(this, R.array.search_cities, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySelectorSpinner.adapter = adapter

        // Setup Recycler view
        resultsRecyclerView.layoutManager = LinearLayoutManager(this)
        resultsRecyclerView.adapter = resultsAdapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val drawable = progressImage.drawable
        if (drawable is Animatable) {
            drawable.start()
        } else {
            Timber.d { "got drawable $drawable which is not Animatable" }
        }
    }

    override fun onDetachedFromWindow() {
        val drawable = progressImage.drawable
        if (drawable is Animatable) {
            drawable.stop()
        }
        super.onDetachedFromWindow()
    }

    override fun showFiveDayWeather(weatherResponses: List<WeatherResponseItem>) {
        Timber.v { "Show 5 day weather" }
        resultsAdapter.setWeather(weatherResponses)
    }

    override fun showError() {
        Timber.w { "Show error" }
        Toast.makeText(this, R.string.unknown_error, Toast.LENGTH_LONG).show()
    }

    override fun showNetworkError() {
        Timber.w { "Show network error" }
        Toast.makeText(this, R.string.netwrok_error, Toast.LENGTH_LONG).show()
    }

    override fun showProgress(shouldShow: Boolean) {
        Timber.v { "Show progress $shouldShow" }
        progressImage.visibility = if (shouldShow) View.VISIBLE else View.GONE
        resultsRecyclerView.visibility = if (shouldShow) View.GONE else View.VISIBLE
    }

    override fun onCitySelected(): Observable<String> =
            RxAdapterView.itemSelections<SpinnerAdapter>(citySelectorSpinner)
                    .filter { position -> position > -1 }
                    .map { position ->
                        val cities = resources.getStringArray(R.array.search_cities)
                        cities[position]
                    }

    override fun onRetryClicked(): Observable<Boolean> = Observable.just(false)
}
