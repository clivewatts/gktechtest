package za.co.clivewatts.gkweather.api.repository

import com.haroldadmin.cnradapter.NetworkResponse
import za.co.clivewatts.gkweather.api.models.ErrorResponse
import za.co.clivewatts.gkweather.api.models.weather.ModelResponseWeather
import za.co.clivewatts.gkweather.models.BaseResponse

interface RepositoryInterface {

    suspend fun getWeather(lat: String, long: String) : BaseResponse<ModelResponseWeather>

}