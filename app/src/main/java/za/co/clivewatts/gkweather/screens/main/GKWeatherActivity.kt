package za.co.clivewatts.gkweather.screens.main

import android.Manifest
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.java.KoinJavaComponent.inject
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.listener.PermissionDeniedResponse

import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import za.co.clivewatts.gkweather.R
import za.co.clivewatts.gkweather.databinding.ActivityMainBinding


val mainScope = MainScope()
val presenter : GKWeatherPresenter by inject(GKWeatherPresenter::class.java)

class GKWeatherActivity : AppCompatActivity(), GKWeatherViewModel, OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    lateinit var map : GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attachView(this, lifecycle, mainScope)
        presenter.performInit()
        binding.refresh.setOnRefreshListener {
            presenter.refresh()
        }
    }

    override fun showLoading(hide: Boolean) {
        binding.refresh.isRefreshing = !hide
    }

    override fun checkPermissions(showRational: Boolean) {
        val mainPermissionListener = object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                            presenter.onPermissionGranted()
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                            presenter.onPermissionDenied()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                        ) { /* ... */
                        }
                    }

            val dialogPermissionListener: PermissionListener =
                DialogOnDeniedPermissionListener.Builder
                    .withContext(this@GKWeatherActivity)
                    .withTitle("Location Permission")
                    .withMessage("We need to access your location to provide accurate weather information.\nPlease enable this setting in your app settings.")
                    .withButtonText(android.R.string.ok)
                    .withIcon(za.co.clivewatts.gkweather.R.mipmap.ic_launcher)
                    .build()

        val compositePermissionListener: CompositePermissionListener = CompositePermissionListener(mainPermissionListener, dialogPermissionListener)

        Dexter.withContext(this@GKWeatherActivity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(compositePermissionListener)
            .check();
    }

    override fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun loadViewData(transformWeatherToViewWeather: ViewWeatherModel) {
        mainScope.launch {
            withContext(Dispatchers.Main) {
                binding.humidity.text = transformWeatherToViewWeather.humidity
                binding.temp.text = transformWeatherToViewWeather.temp
                binding.cloudCover.text = transformWeatherToViewWeather.cloudCover
                Glide.with(this@GKWeatherActivity).load(transformWeatherToViewWeather.image).into(binding.icWeather)
                val cameraUpdate = CameraUpdateFactory.newLatLng(transformWeatherToViewWeather.location)
                map.moveCamera(cameraUpdate)
                map.addMarker(MarkerOptions().position(transformWeatherToViewWeather.location))
                presenter.resolveAddress(transformWeatherToViewWeather.location)
            }
        }

    }

    override fun showApiError(errorMessage: String) {
        Snackbar.make(this, binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun showLoadingError(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_LONG).show()

    }

    override fun setAddress(address: Address) {
        binding.address.text = address.getAddressLine(0)
    }

    override fun onMapReady(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        this.map = map
    }
}