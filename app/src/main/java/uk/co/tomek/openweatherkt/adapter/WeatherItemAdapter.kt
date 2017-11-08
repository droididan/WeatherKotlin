package uk.co.tomek.openweatherkt.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.ajalt.timberkt.Timber
import uk.co.tomek.openweatherkt.R
import uk.co.tomek.openweatherkt.extensions.loadWeatherIcon
import uk.co.tomek.openweatherkt.model.WeatherResponseItem
import java.util.*

/**
 * Recycler view adapter for a weather results line.
 */
class WeatherItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var weatherResponseItems = listOf<WeatherResponseItem>()
        set(value) {
            field = value
            Timber.v { "Set new weather items :$value" }
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherCellViewHolder) {
            holder.bind(weatherResponseItems[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_cell, parent, false)
        return WeatherCellViewHolder(view)
    }

    override fun getItemCount(): Int = weatherResponseItems.size

    class WeatherCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.textview_item_temperature)
        lateinit var temperature: TextView
        @BindView(R.id.textview_item_time)
        lateinit var time: TextView
        @BindView(R.id.imageview_item_icon)
        lateinit var icon: ImageView

        init {
            Timber.v { "WeatherCellViewHolder init" }
            ButterKnife.bind(this, itemView)
        }

        fun bind(item: WeatherResponseItem) {
            Timber.v { "bind item $item" }
            time.text = item.dt_txt.substring(11, 16)
            temperature.text = String.format(Locale.UK, "%.1f\u00B0", item.main.temp)
            icon.loadWeatherIcon(item.weather[0].icon)
        }
    }
}