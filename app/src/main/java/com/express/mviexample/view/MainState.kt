package com.express.mviexample.view

import com.express.mviexample.model.Animal

/**
 *
 * State of main screen
 *
 */
sealed class MainState{
    object Idle:MainState()
    object Loading:MainState()
    data class Animals(val animals: List<Animal>): MainState()
    data class Error(val error:String?):MainState()
}
