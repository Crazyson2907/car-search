package com.example.carsearch.domain.network.model

import com.example.carsearch.domain.core.model.CommonList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuildDateResponse(
    @Json(name = "wkda")
    val wkda: CommonList
)
