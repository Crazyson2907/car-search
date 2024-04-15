package com.example.carsearch.domain.network.model

import com.example.carsearch.domain.core.model.CommonList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ManufacturerResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "totalPageCount")
    val totalPageCount: Int,
    @Json(name = "wkda")
    val wkda: CommonList
)
