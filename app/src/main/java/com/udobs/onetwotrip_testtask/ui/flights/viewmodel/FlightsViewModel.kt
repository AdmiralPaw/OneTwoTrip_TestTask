package com.udobs.onetwotrip_testtask.ui.flights.viewmodel

import androidx.lifecycle.*
import com.udobs.onetwotrip_testtask.data.model.Flight
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.usecases.GetFlightsUseCase
import com.udobs.onetwotrip_testtask.utils.Resource
import kotlinx.coroutines.launch

class FlightsViewModel(
    private val getFlightsUseCase: GetFlightsUseCase
) : ViewModel() {
    private val _flightsToShow = MutableLiveData<Resource<List<Flight>>>()
    val flightsToShow: LiveData<Resource<List<Flight>>> = _flightsToShow

    fun getFlights() = viewModelScope.launch {
        _flightsToShow.postValue(Resource.loading())
        _flightsToShow.postValue(getFlightsUseCase.performAction())
    }
}