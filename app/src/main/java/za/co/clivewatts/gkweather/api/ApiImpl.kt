package za.co.clivewatts.gkweather.api

import za.co.clivewatts.gkweather.api.interfaces.WeatherApi
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit

fun getWeatherClient(retrofit: Retrofit): WeatherApi {
    return retrofit.create(WeatherApi::class.java)
}
