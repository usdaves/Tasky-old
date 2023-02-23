package io.usdaves.auth.repository

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

interface AuthRepository {
  suspend fun signIn(email: String, password: String): SignInResult
  suspend fun signUp(displayName: String, email: String, password: String): SignUpResult
}
