package io.usdaves.auth.repository

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

sealed interface SignUpResult {
  object NoNetworkConnection : SignUpResult
  object EmptyDisplayName : SignUpResult
  object TooShortDisplayName : SignUpResult
  object TooLongDisplayName : SignUpResult
  object EmptyEmail : SignUpResult
  object InvalidEmail : SignUpResult
  object EmailAlreadyInUse : SignUpResult
  object EmptyPassword : SignUpResult
  object TooShortPassword : SignUpResult
  object TooLongPassword : SignUpResult
  data class UnknownError(val message: String) : SignUpResult
  object Success : SignUpResult
}
