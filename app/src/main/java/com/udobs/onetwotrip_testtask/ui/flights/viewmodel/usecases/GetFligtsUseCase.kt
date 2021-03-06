package com.udobs.onetwotrip_testtask.ui.flights.viewmodel.usecases

import com.udobs.onetwotrip_testtask.data.model.flights.Flight
import com.udobs.onetwotrip_testtask.data.repository.flights.FlightsRepository
import com.udobs.onetwotrip_testtask.utils.Resource
import java.util.*

class GetFlightsUseCase(private val repository: FlightsRepository) {
    // 5 minutes in ms
    private val dataRefreshTimeout = 5 * 60 * 1000

    suspend fun performAction(): Resource<List<Flight>> {
        repository.flightsCashed?.let {
            if (Date().time - repository.lastRequestTimestamp > dataRefreshTimeout)
                return it
        }
        return repository.getFlights()
    }
}