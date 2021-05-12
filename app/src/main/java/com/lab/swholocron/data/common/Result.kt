package com.lab.swholocron.data.common

sealed class Result<T, E>

data class Success<T, E>(val value: T) : Result<T, E>()
data class Failure<T, E>(val reason: E) : Result<T, E>()