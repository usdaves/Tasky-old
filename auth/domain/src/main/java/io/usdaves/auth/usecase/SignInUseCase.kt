package io.usdaves.auth.usecase

import io.usdaves.auth.repository.AuthRepository
import io.usdaves.auth.repository.SignInResult
import io.usdaves.core.matcher.EmailMatcher
import io.usdaves.logger.Logger
import javax.inject.Inject

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

class SignInUseCase @Inject constructor(
  private val repository: AuthRepository,
  private val emailMatcher: EmailMatcher,
  private val logger: Logger,
) {

  init {
    // Just to monitor lifetime of the dependencies
    logger.i("SignInUseCase instance created")
  }

  suspend operator fun invoke(email: String, password: String): SignInResult {
    logger.d("SignInUseCase invoked with Email: $email, Password: $password")
    val invalidInputsResult = getInvalidInputResult(email, password)
    if (invalidInputsResult != null) {
      return invalidInputsResult
    }
    return repository.signIn(email, password)
  }

  private fun getInvalidInputResult(email: String, password: String): SignInResult? {
    return when {
      email.isEmpty() -> SignInResult.EmptyEmail
      password.isEmpty() -> SignInResult.EmptyPassword
      !emailMatcher.matches(email) -> SignInResult.InvalidCredentials
      password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH -> SignInResult.InvalidCredentials
      else -> null
    }
  }

  // If you want to change these values, consider changing also in the SignUpUseCase file.
  internal companion object {
    const val MIN_PASSWORD_LENGTH = 8
    const val MAX_PASSWORD_LENGTH = 64
  }
}
