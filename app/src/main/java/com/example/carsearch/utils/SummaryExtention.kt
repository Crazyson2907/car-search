package com.example.carsearch.utils

import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.core.model.main.Year

fun Manufacturer.displayName(): String = this.name

fun Model.displayName(): String = this.name

fun Year.displayName(): String = this.name

fun Manufacturer.displaySelection(): String = "Choose the model of your $name"

fun CarSummary.displaySelection(): String {
    return "Choose the year of your ${manufacturer.name} ${model.name}"
}