package za.co.clivewatts.gkweather.utils

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.coroutines.resumeWithException

private const val UPDATE_INTERVAL_SECS = 10L
private const val FASTEST_UPDATE_INTERVAL_SECS = 2L

@ExperimentalCoroutinesApi
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocation(): Location =

    // Create a new coroutine that can be cancelled
    suspendCancellableCoroutine<Location> { continuation ->

        // Add listeners that will resume the execution of this coroutine
        lastLocation.addOnSuccessListener { location ->
            // Resume coroutine and return location
            if (location == null) {
                continuation.resumeWithException(Exception("Location Error"))
                return@addOnSuccessListener
            }
            continuation.resume(location, null)
        }.addOnFailureListener { e ->
            // Resume the coroutine by throwing an exception
            continuation.resumeWithException(e)
        }

        // End of the suspendCancellableCoroutine block. This suspends the
        // coroutine until one of the callbacks calls the continuation parameter.
    }

class LocationUpdatesUseCase (
    private val client: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    fun fetchUpdates(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create()

        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation

                this@callbackFlow.trySend(location).isSuccess
            }
        }

        client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
        awaitClose { client.removeLocationUpdates(callBack) }
    }

    companion object {
        private const val UPDATE_INTERVAL_SECS = 10L
        private const val FASTEST_UPDATE_INTERVAL_SECS = 2L
    }
}