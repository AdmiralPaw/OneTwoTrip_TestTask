package com.udobs.onetwotrip_testtask.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlightPrice(
    @SerializedName("type")
    val type: FlightPriceType,
    @SerializedName("amount")
    val amount: Int
) : Serializable {
    enum class FlightPriceType(
        val value: String
    ) : Serializable {
        @SerializedName("bussiness")
        BUSINESS("бизнесс"),
        @SerializedName("economy")
        ECONOMY("эконом"),
    }
}
