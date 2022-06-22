package com.express.mviexample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * Creating retrofit instance
 */
object AnimalService {
    const val BASE_URL = "https://raw.githubusercontent.com/CatalinStefan/animalApi/master/"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api:AnimalAPI = getRetrofit().create(AnimalAPI::class.java)
}