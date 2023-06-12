package com.speer.githubusers.domain.usecase.base

interface UseCase<T> {

    sealed interface Result<T> {
        data class Success<T>(val value: T) : Result<T>
        data class Error<T>(val e: Throwable) : Result<T>
    }

    suspend fun execute(): Result<T>
}
