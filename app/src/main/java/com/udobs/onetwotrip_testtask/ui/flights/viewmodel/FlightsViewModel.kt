package com.udobs.onetwotrip_testtask.ui.flights.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udobs.onetwotrip_testtask.data.model.flights.Flight
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.usecases.GetFlightsUseCase
import com.udobs.onetwotrip_testtask.utils.Resource
import kotlinx.coroutines.launch

class FlightsViewModel(
    private val getFlightsUseCase: GetFlightsUseCase
) : ViewModel() {
    private val _flightsToShow = MutableLiveData<Resource<List<Flight>>>()
    val flightsToShow: LiveData<Resource<List<Flight>>> = _flightsToShow

    init {
        getFlights()
    }

    fun getFlights() = viewModelScope.launch {
        _flightsToShow.postValue(Resource.loading())

        val flights = getFlightsUseCase.performAction()
        _flightsToShow.postValue(flights)
    }
}