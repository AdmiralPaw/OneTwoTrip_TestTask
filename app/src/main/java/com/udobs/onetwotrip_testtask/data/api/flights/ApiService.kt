package com.udobs.onetwotrip_testtask.data.api.flights

import com.udobs.onetwotrip_testtask.data.model.Flight
import retrofit2.http.GET

interface ApiService {
    @GET("ott/search")
    suspend fun getFlights(): List<Flight>
}