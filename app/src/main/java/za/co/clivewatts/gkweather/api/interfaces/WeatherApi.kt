package za.co.clivewatts.gkweather.api.interfaces

import com.haroldadmin.cnradapter.NetworkResponse
import za.co.clivewatts.gkweather.api.models.weather.ModelResponseWeather
import za.co.clivewatts.gkweather.boilerplate.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import za.co.clivewatts.gkweather.api.models.ErrorResponse

interface WeatherApi {
    @GET("2.5/weather")
    suspend fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon : String, @Query("appid") key : String = Constants.API_KEY) : NetworkResponse<ModelResponseWeather, ErrorResponse>
}