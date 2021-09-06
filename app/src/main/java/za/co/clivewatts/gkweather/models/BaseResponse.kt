package za.co.clivewatts.gkweather.models

import retrofit2.Response
import za.co.clivewatts.gkweather.api.models.ErrorResponse

data class BaseResponse<T>(val body: T?, val error: ErrorResponse?, val errorMessage: String?)