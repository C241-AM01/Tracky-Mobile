package com.megalogic.tracky.data

sealed class ResultState<out T>{
    data class Success<out T>(val data: T): ResultState<T>()
    data class Error(val exception: String): ResultState<Nothing>()
    object Loading: ResultState<Nothing>()
}