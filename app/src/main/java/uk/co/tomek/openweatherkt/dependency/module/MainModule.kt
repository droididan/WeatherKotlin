package uk.co.tomek.openweatherkt.dependency.module

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.tomek.openweatherkt.BuildConfig
import uk.co.tomek.openweatherkt.network.OpenWeatherNetworkService
import uk.co.tomek.openweatherkt.rx.SchedulersProvider
import javax.inject.Singleton

/**
 * Main app dagger dependency module.
 */
@Module
class MainModule {

    @Provides
    @Singleton
    internal fun provideSchedulersProvider() = SchedulersProvider()

    @Provides
    @Singleton
    internal fun provideCallAdapterFactory(schedulersProvider: SchedulersProvider): CallAdapter.Factory
            = RxJava2CallAdapterFactory.createWithScheduler(schedulersProvider.io())

    @Provides
    @Singleton
    internal fun provideBaseUrl(): BaseUrl = BuildConfig.SERVER_URL

    @Provides
    @Singleton
    internal fun provideHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            // add HTTP logging
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(baseUrl: BaseUrl,
                                 converterFactory: Converter.Factory,
                                 callAdapterFactory: CallAdapter.Factory,
                                 httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(httpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    internal fun provideOpenWeatherNetworkService(retrofit: Retrofit): OpenWeatherNetworkService =
            retrofit.create<OpenWeatherNetworkService>(OpenWeatherNetworkService::class.java)
}
typealias BaseUrl = String
