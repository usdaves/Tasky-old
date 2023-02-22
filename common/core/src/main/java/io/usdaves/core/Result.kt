package io.usdaves.core

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

sealed class Result<out T> {

  data class Success<out T> internal constructor(val data: T) : Result<T>()
  data class Failure internal constructor(val throwable: Throwable) : Result<Nothing>()

  companion object {

    fun <T> success(data: T): Result<T> = Success(data)

    fun failure(throwable: Throwable): Result<Nothing> = Failure(throwable)

    fun failure(message: String?): Result<Nothing> = Failure(Throwable(message))

    fun failure(message: String?, cause: Throwable?): Result<Nothing> =
      Failure(Throwable(message, cause))
  }
}
