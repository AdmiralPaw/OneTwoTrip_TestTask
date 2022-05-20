package com.udobs.onetwotrip_testtask.data.repository.flights

import com.udobs.onetwotrip_testtask.data.api.flights.ApiHelper
import com.udobs.onetwotrip_testtask.data.model.flights.Flight
import com.udobs.onetwotrip_testtask.utils.Resource
import java.util.*

class FlightsRepository(private val apiHelper: ApiHelper) {
    var flightsCashed: Resource<List<Flight>>? = null
    var lastRequestTimestamp: Long = 0

    suspend fun getFlights() = try {
        val flights = apiHelper.getFlights()
        val sortedFlights = flights.sortedBy { it.cheapestPrice }.sortedBy { it.transferCount }

        flightsCashed = Resource.success(sortedFlights)
        lastRequestTimestamp = Date().time

        Resource.success(sortedFlights)
    } catch (e: Exception) {
        Resource.error(e)
    }
}