package za.co.clivewatts.gkweather.api.repository

import com.haroldadmin.cnradapter.NetworkResponse
import org.koin.java.KoinJavaComponent.inject
import za.co.clivewatts.gkweather.api.models.weather.ModelResponseWeather
import za.co.clivewatts.gkweather.api.getWeatherClient
import za.co.clivewatts.gkweather.api.interfaces.WeatherApi
import za.co.clivewatts.gkweather.api.models.ErrorResponse
import za.co.clivewatts.gkweather.models.BaseResponse

val weatherApi : WeatherApi by inject(WeatherApi::class.java)
class RepositoryImpl : RepositoryInterface {
    override suspend fun getWeather(lat: String, long: String): BaseResponse<ModelResponseWeather> {
        return when(val response = weatherApi.getWeatherData(lat,long)) {
            is NetworkResponse.Success -> {
                BaseResponse(response.body, null, null)
            }
            is NetworkResponse.ServerError -> {
                BaseResponse(null, response.body, null)
            }
            is NetworkResponse.NetworkError-> {
                BaseResponse(null, null, response.error.toString())
            }
            is NetworkResponse.UnknownError -> {
                BaseResponse(null, null, response.error.toString())
            }
        }
    }
}