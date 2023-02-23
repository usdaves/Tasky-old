package io.usdaves.core.util

import io.usdaves.core.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

inline fun <T> Result<T>.onEach(block: Result<T>.() -> Unit) = also(block)

inline fun <T> Result<T>.onSuccess(block: Result.Success<T>.() -> Unit) = onEach {
  if (this is Result.Success) block(this)
}

inline fun <T> Result<T>.onFailure(block: Result.Failure.() -> Unit) = onEach {
  if (this is Result.Failure) block()
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
  is Result.Success -> Result.success(transform(data))
  is Result.Failure -> Result.failure(throwable)
}

fun <T> Flow<T>.asResultFlow(): Flow<Result<T>> = map { data -> Result.success(data) }
  .catch { throwable -> emit(Result.failure(throwable)) }

inline fun <T> resultOf(block: () -> T): Result<T> = try {
  Result.success(block())
} catch (cancellation: CancellationException) {
  throw cancellation
} catch (throwable: Throwable) {
  Result.failure(throwable)
}

inline val Result<*>.isSuccess: Boolean
  get() = this is Result.Success<*>

inline val Result<*>.isFailure: Boolean
  get() = this is Result.Failure
