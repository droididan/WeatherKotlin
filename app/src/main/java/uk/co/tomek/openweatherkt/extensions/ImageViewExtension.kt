package uk.co.tomek.openweatherkt.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * [ImageView] extensions.
 */
fun ImageView.loadWeatherIcon(iconName: String) {
    val iconUrl = String.format("http://openweathermap.org/img/w/%s.png", iconName)
    Picasso.with(this.context).load(iconUrl).into(this)
}