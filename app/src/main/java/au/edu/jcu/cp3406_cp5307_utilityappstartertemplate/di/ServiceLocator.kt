package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.di

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherApi
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.WeatherViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Simple dependency injection container
 * Provides singleton instances of required objects
 */
object ServiceLocator {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
    }

    private val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }

    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherApi)
    }

    /**
     * Create a new instance of WeatherViewModel
     */
    fun createWeatherViewModel(): WeatherViewModel {
        return WeatherViewModel(weatherRepository)
    }
}

