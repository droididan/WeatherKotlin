package uk.co.tomek.openweatherkt.dependency.component

import dagger.Component
import uk.co.tomek.openweatherkt.MainActivity
import uk.co.tomek.openweatherkt.dependency.module.AndroidModule
import uk.co.tomek.openweatherkt.dependency.module.MainModule
import javax.inject.Singleton

/**
 * Main dagger component.
 */
@Component(
        modules = arrayOf(
                AndroidModule::class,
                MainModule::class
        )
)
@Singleton
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}