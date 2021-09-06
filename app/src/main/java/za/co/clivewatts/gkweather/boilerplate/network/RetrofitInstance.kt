package za.co.clivewatts.gkweather.boilerplate.network

import za.co.clivewatts.gkweather.boilerplate.Constants
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val gson : Gson by inject(Gson::class.java);
        fun retrofit(): Retrofit {
            val httpClient = OkHttpClient.Builder();
            httpClient.addInterceptor(HttpLoggingInterceptor())
            return Retrofit.Builder()
                .baseUrl(Constants.WEATHER_API_URI)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}