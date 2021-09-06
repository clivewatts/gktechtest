package za.co.clivewatts.gkweather.utils

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
class GeneralUtils {
}