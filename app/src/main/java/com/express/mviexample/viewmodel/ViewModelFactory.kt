package com.express.mviexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.express.mviexample.api.AnimalAPI
import com.express.mviexample.repositorty.AnimalRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val api:AnimalAPI): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AnimalViewModel::class.java)){
            return AnimalViewModel(AnimalRepository(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}