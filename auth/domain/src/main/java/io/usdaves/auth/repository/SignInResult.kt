package io.usdaves.auth.repository

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

sealed interface SignInResult {
  object NoNetworkConnection : SignInResult
  object EmptyEmail : SignInResult
  object EmptyPassword : SignInResult
  object InvalidCredentials : SignInResult
  data class UnknownError(val message: String) : SignInResult
  object Success : SignInResult
}
