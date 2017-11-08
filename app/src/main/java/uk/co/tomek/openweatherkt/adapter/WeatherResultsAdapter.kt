package uk.co.tomek.openweatherkt.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
import uk.co.tomek.openweatherkt.model.WeatherResponseItem
import uk.co.tomek.openweatherkt.view.WeatherDayView

/**
 * Adapter for the weather results list.
 */
class WeatherResultsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var weatherDays = mutableListOf<Pair<String, Set<WeatherResponseItem>>>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is WeatherDayViewHolder) {
            Timber.v { "Binding weather day:$position" }
            // TODO: Split weather items into days
            holder.bindWeatherDay(weatherDays[position])
        }
    }

    override fun getItemCount(): Int = weatherDays.size // forecast days

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = WeatherDayView(parent.context)
        return WeatherDayViewHolder(view)
    }

    fun setWeather(weatherResponses: List<WeatherResponseItem>) {

        // split into different days
        val days = mutableListOf<String>()
        var currentDaySet = emptySet<WeatherResponseItem>()
        weatherResponses.forEach { item ->
            val day = item.dt_txt.substring(0, 10)
            if (!days.contains(day)) { // new day
                Timber.d { "New day $day" }
                if (currentDaySet.isNotEmpty()) { // add previous day
                    weatherDays.add(days[days.size - 1] to currentDaySet)
                    currentDaySet = emptySet()
                }
                days.add(day)
            }
            currentDaySet += item
        }
        Timber.v { "New weather days size:${weatherDays.size} data: $weatherDays" }
        notifyDataSetChanged()
    }

    class WeatherDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindWeatherDay(items: Pair<String, Set<WeatherResponseItem>>) {
            (itemView as WeatherDayView).setWeatherItems(items)
        }
    }
}