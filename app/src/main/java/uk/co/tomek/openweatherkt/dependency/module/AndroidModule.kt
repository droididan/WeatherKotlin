package uk.co.tomek.openweatherkt.dependency.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module providing android related objects.
 */
@Module
class AndroidModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context
}