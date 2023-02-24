package io.usdaves.test

import io.usdaves.auth.repository.AuthRepository
import io.usdaves.auth.repository.SignInResult
import io.usdaves.auth.repository.SignUpResult

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

class FakeAuthRepository : AuthRepository {

  override suspend fun signIn(email: String, password: String): SignInResult {
    return SignInResult.Success
  }

  override suspend fun signUp(displayName: String, email: String, password: String): SignUpResult {
    return SignUpResult.Success
  }
}
