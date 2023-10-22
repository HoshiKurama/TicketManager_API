package com.github.hoshikurama.ticketmanager.api.utilities

import java.lang.Exception

@Suppress("Unused")
sealed class Result<out T> {
    class Success<out T>(val value: T): Result<T>()
    class Failure(val error: Exception): Result<Nothing>()

    inline fun <U> map(f: (T) -> U): Result<U> = when (this) {
        is Failure -> this
        is Success<T> ->
            try { Success(f(value)) }
            catch (e: Exception) { Failure(e) }
    }

    fun getOrThrow(): T = when (this) {
        is Failure -> throw error
        is Success -> value
    }

    fun getOrDefault(default: @UnsafeVariance T): T = when (this) {
        is Failure -> default
        is Success -> value
    }
}

@Suppress("Unused")
inline fun <T: Any, U: Any> Result<T?>.mapIfPresent(f: (T) -> U): Result<U?> = when (this) {
    is Result.Failure -> this
    is Result.Success<T?> ->
        if (value != null)
            try {
                Result.Success(f(value))
            }
            catch (e: Exception) {
                Result.Failure(e)
            }
        else Result.Success(null)
}