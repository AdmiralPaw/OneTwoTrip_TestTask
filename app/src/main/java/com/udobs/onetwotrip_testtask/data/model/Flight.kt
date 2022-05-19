package com.udobs.onetwotrip_testtask.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Flight(
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("prices")
    val prices: List<FlightPrice>,
    @SerializedName("trips")
    val trips: List<FlightTrip>,
) : Serializable {
    val from: FlightTrip.Airport
        get() = trips.first().from

    val to: FlightTrip.Airport
        get() = trips.last().to

    val cheapestPrice: Int
        get() = prices.minOf { it.amount }

    val withTransfer: Boolean
        get() = trips.size > 1

    val transferCount: Int
        get() = trips.size - 1

    enum class Currency(val value: String) : Serializable {
        @SerializedName("RUB")
        RUB("₽"),

        @SerializedName("USD")
        USD("\$"),
    }

}
