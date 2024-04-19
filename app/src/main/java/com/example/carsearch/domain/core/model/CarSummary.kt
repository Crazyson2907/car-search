package com.example.carsearch.domain.core.model

import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.core.model.main.Year
import java.io.Serializable

data class CarSummary(
    val manufacturer: Manufacturer = Manufacturer(),
    val model: Model = Model(),
    val year: Year = Year()
) : Serializable
