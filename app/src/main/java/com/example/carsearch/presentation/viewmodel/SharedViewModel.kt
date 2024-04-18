package com.example.carsearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

) : ViewModel(){

    private val selectedManufacturerId = MutableLiveData<String>()

    fun selectManufacturerId(manufacturerId: String) {
        selectedManufacturerId.value = manufacturerId
    }

    fun getSelectedManufacturerId(): LiveData<String> = selectedManufacturerId
}