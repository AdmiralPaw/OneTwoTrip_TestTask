package com.udobs.onetwotrip_testtask.data.model.flights

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlightTrip(
    @SerializedName("from")
    val from: Airport,
    @SerializedName("to")
    val to: Airport
) : Serializable {
    enum class Airport(val value: String, val townName: String) : Serializable {
        @SerializedName("SVO")
        SVO("Шереметьево", "Москва"),

        @SerializedName("JFK")
        JFK("Аэропорт имени Джона Кеннеди", "Нью-Йорк"),

        @SerializedName("EWR")
        EWR("Ньюарк", "Нью-Йорк"),

        @SerializedName("HND")
        HND("Токио-Ханеда", "Токио"),

        @SerializedName("NRT")
        NRT("Нарита", "Токио"),

        @SerializedName("DME")
        DME("Домодедово", "Москва"),

        @SerializedName("DOH")
        DOH("Хамад", "Доха"),

        @SerializedName("LHR")
        LHR("Лондон Хитроу", "Лондон"),

        @SerializedName("FRA")
        FRA("Франкфурт", "Гессен"),
    }
}