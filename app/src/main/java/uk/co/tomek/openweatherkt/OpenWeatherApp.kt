package uk.co.tomek.openweatherkt

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import uk.co.tomek.openweatherkt.dependency.component.DaggerMainComponent
import uk.co.tomek.openweatherkt.dependency.component.MainComponent
import uk.co.tomek.openweatherkt.dependency.module.AndroidModule

/**
 * Main application class.
 */
class OpenWeatherApp : Application() {

    val mainComponent: MainComponent = DaggerMainComponent.builder()
            .androidModule(AndroidModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()

        // initialise Leak Canary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}