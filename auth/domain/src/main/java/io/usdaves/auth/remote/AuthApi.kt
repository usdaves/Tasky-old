package io.usdaves.auth.remote

import io.usdaves.core.Result

interface AuthApi {
  suspend fun signIn(email: String, password: String): Result<String>
  suspend fun signUp(email: String, password: String): Result<String>
}
