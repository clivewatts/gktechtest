package za.co.clivewatts.gkweather.boilerplate

class Constants {
    companion object {
        //Normally I would put this in remote config for security and simple endpoint manipulation ut I don't have RC available on my firebase plan
        const val WEATHER_API_URI = "https://api.openweathermap.org/data/"
        const val API_KEY = "53f9d8e4213222cf517d86dc406d67fc"
        const val ICON_URL = "https://openweathermap.org/img/wn/"
    }
}