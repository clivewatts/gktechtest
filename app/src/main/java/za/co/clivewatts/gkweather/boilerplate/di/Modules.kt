package za.co.clivewatts.gkweather.boilerplate.di

import za.co.clivewatts.gkweather.api.repository.RepositoryImpl
import za.co.clivewatts.gkweather.api.repository.RepositoryInterface
import za.co.clivewatts.gkweather.boilerplate.network.RetrofitInstance
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import za.co.clivewatts.gkweather.api.getWeatherClient
import za.co.clivewatts.gkweather.api.interfaces.WeatherApi
import za.co.clivewatts.gkweather.screens.main.GKWeatherPresenter

val appModule = module {

    // Gson is relatively expensive to init so lets only do this once #GloballlyAvailable
    single<Gson> { Gson() }

    single<Retrofit> { RetrofitInstance.retrofit()}

    single { RepositoryImpl() }

    single<GKWeatherPresenter> { GKWeatherPresenter() }

    single<WeatherApi> {
        getWeatherClient(get())
    }

}