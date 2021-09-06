package za.co.clivewatts.gkweather.screens.main

import android.location.Address

interface GKWeatherViewModel {
    fun showLoading(hide: Boolean = false)
    fun checkPermissions(showRational : Boolean = false)
    fun initMap()
    abstract fun loadViewData(transformWeatherToViewWeather: ViewWeatherModel)
    abstract fun showApiError(errorMessage: String)
    abstract fun showLoadingError(message: String)
    abstract fun setAddress(address: Address)
}