package za.co.clivewatts.gkweather.screens.main

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.java.KoinJavaComponent
import za.co.clivewatts.gkweather.api.models.weather.ModelResponseWeather
import za.co.clivewatts.gkweather.api.repository.RepositoryImpl
import za.co.clivewatts.gkweather.api.repository.RepositoryInterface
import za.co.clivewatts.gkweather.boilerplate.BasePresenter
import za.co.clivewatts.gkweather.models.BaseResponse
import za.co.clivewatts.gkweather.utils.LocationUpdatesUseCase
import java.util.*

val repo : RepositoryInterface by KoinJavaComponent.inject(RepositoryImpl::class.java)
@ExperimentalCoroutinesApi
class GKWeatherPresenter() : BasePresenter<GKWeatherViewModel>() {

    fun performInit() {
        view()?.checkPermissions()
    }

    fun onPermissionGranted() {
        getLatLongOneShot()
    }

    fun onPermissionDenied() {

    }

    fun refresh() {
        getLatLongOneShot()
    }

    @SuppressLint("MissingPermission")
    private fun getLatLongOneShot() {
        view()?.showLoading()
        mainScope.launch {
            view()?.initMap()
            withContext(Dispatchers.IO) {
                val fusedLocation =  LocationServices.getFusedLocationProviderClient(view() as GKWeatherActivity)
                LocationUpdatesUseCase(fusedLocation).fetchUpdates().collect {
                    val weather = repo.getWeather(it.latitude.toString(), it.longitude.toString())
                    handleResponse(weather)
                }
            }
        }
    }

    private fun handleResponse(weather: BaseResponse<ModelResponseWeather>) {
        view()?.showLoading(true)
        if (weather.error != null) {
            view()?.showLoadingError(weather.error.message)
            return
        }
        if (weather.errorMessage != null) {
            view()?.showApiError(weather.errorMessage)
            return
        }
        if (weather.body != null) {
            view()?.loadViewData(transformWeatherToViewWeather(weather.body))
        }
    }

    fun resolveAddress(location: LatLng) {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(view() as GKWeatherActivity, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )

        if (addresses.isNotEmpty()) {
            view()?.setAddress(addresses[0])
        }

    }

}