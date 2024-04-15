package com.example.carsearch.domain.core.base

interface BaseMapper<in I, out O> {
    suspend fun map(input: I): O
}