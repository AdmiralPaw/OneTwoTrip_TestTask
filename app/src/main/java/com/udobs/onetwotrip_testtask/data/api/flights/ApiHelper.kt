package com.udobs.onetwotrip_testtask.data.api.flights

class ApiHelper(private val apiService: ApiService) {
    suspend fun getFlights() = apiService.getFlights()
}