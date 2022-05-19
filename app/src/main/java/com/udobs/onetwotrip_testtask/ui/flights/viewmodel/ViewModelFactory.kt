package com.udobs.onetwotrip_testtask.ui.flights.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udobs.onetwotrip_testtask.data.api.flights.ApiHelper
import com.udobs.onetwotrip_testtask.data.repository.flights.FlightsRepository
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.usecases.GetFlightsUseCase

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightsViewModel::class.java)) {
            return FlightsViewModel(GetFlightsUseCase(FlightsRepository(apiHelper = apiHelper))) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}