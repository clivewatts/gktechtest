package za.co.clivewatts.gkweather.screens.main

import android.location.Location
import com.google.android.gms.maps.model.LatLng

data class ViewWeatherModel(val temp : String,
                            val humidity : String,
                            val cloudCover: String,
                            val image : String,
                            val location : LatLng,
)
