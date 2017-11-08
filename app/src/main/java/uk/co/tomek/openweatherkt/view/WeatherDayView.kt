package uk.co.tomek.openweatherkt.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import uk.co.tomek.openweatherkt.R
import uk.co.tomek.openweatherkt.adapter.WeatherItemAdapter
import uk.co.tomek.openweatherkt.model.WeatherResponseItem
import java.text.SimpleDateFormat
import java.util.*

/**
 * Custom view representing a day of weather.
 */
class WeatherDayView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    @BindView(R.id.weather_day_title)
    lateinit var title: TextView

    @BindView(R.id.weather_day_recycler_view)
    lateinit var recyclerView: RecyclerView

    private var weatherItemsAdapter: WeatherItemAdapter

    init {
        orientation = VERTICAL
        val view = LayoutInflater.from(context).inflate(R.layout.weather_day_layout, this, true)
        ButterKnife.bind(this, view)
        weatherItemsAdapter = WeatherItemAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
            adapter = weatherItemsAdapter
        }
    }

    fun setWeatherItems(items: Pair<String, Set<WeatherResponseItem>>) {
        val dayTime = SimpleDateFormat("yyyy-MM-dd", Locale.UK).parse(items.first).time
        title.text = DateUtils.getRelativeDateTimeString(context, dayTime, DateUtils.DAY_IN_MILLIS, DateUtils.DAY_IN_MILLIS + DateUtils.HOUR_IN_MILLIS, DateUtils.FORMAT_SHOW_WEEKDAY)
        weatherItemsAdapter.weatherResponseItems = items.second.toList()
    }
}