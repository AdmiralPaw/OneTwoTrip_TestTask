package com.udobs.onetwotrip_testtask.data.model.flights

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlightPrice(
    @SerializedName("type")
    val type: TicketType,
    @SerializedName("amount")
    val amount: Int
) : Serializable {
    enum class TicketType(val value: String) : Serializable {
        @SerializedName("bussiness")
        BUSINESS("Бизнес"),

        @SerializedName("economy")
        ECONOMY("Эконом"),
    }
}
