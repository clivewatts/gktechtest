package za.co.clivewatts.gkweather.screens.main

import com.google.android.gms.maps.model.LatLng
import za.co.clivewatts.gkweather.api.models.weather.ModelResponseWeather
import za.co.clivewatts.gkweather.boilerplate.Constants
import za.co.clivewatts.gkweather.utils.round

fun transformWeatherToViewWeather(weather: ModelResponseWeather): ViewWeatherModel {
    val main = weather.main
    return ViewWeatherModel(
        (main.temp  -  273.15).round(1).toString(), //Convert k -> c
        main.humidity.toString(),
        "${weather.clouds.all}%",
        "${Constants.ICON_URL}${weather.weather.map { weatherMap -> weatherMap.icon }.first()}@2x.png",
        LatLng(weather.coord.lat, weather.coord.lon)
    )
}