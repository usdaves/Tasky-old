package io.usdaves.auth.repository

import io.usdaves.core.Result

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

interface AuthRepository {

  suspend fun signIn(email: String, password: String): Result<Unit>

  suspend fun signUp(displayName: String, email: String, password: String): Result<Unit>

}
