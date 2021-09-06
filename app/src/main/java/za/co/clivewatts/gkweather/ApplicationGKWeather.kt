package za.co.clivewatts.gkweather

import android.app.Application
import za.co.clivewatts.gkweather.boilerplate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationGKWeather: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@ApplicationGKWeather)
            modules(appModule)
        }
    }

}