package com.express.mviexample.repositorty

import com.express.mviexample.api.AnimalAPI

class AnimalRepository(private val api:AnimalAPI) {
    suspend fun getAnimals() = api.getAnimals()
}