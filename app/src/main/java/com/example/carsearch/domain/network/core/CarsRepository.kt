package com.example.carsearch.domain.network.core


interface CarsRepository {

    suspend fun getCarManufacturers()
}