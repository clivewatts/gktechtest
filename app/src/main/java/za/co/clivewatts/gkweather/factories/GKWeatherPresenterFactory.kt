package za.co.clivewatts.gkweather.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import za.co.clivewatts.gkweather.screens.main.GKWeatherPresenter

class GKWeatherPresenterFactory(
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GKWeatherPresenter() as T
    }
}