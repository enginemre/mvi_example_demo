package com.express.mviexample.api

import com.express.mviexample.model.Animal
import retrofit2.http.GET


interface AnimalAPI {
    @GET("animals.json")
    suspend fun getAnimals():List<Animal>
}
