package com.express.mviexample.view

/**
 *
 * Main screen user intents
 *
 */
sealed class MainIntent {
    object FetchAnimals:MainIntent()
}