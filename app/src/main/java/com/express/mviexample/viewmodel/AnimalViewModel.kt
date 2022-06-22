package com.express.mviexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.express.mviexample.repositorty.AnimalRepository
import com.express.mviexample.view.MainIntent
import com.express.mviexample.view.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AnimalViewModel(private val repository: AnimalRepository): ViewModel() {
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state:StateFlow<MainState>
        get() = _state

    init{
        handleIntent()
    }

    /**
     *
     * handle all intents here
     *
     */
    private fun handleIntent(){
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it){
                    is MainIntent.FetchAnimals -> fetchAnimals()
                }
            }
        }
    }

    /**
     *
     * Fetching animals while fetching change the state
     *
     */
    private fun fetchAnimals() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Animals(repository.getAnimals())
            }catch (e:Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }
}