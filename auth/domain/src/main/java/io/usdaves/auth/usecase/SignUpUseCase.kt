package io.usdaves.auth.usecase

import io.usdaves.auth.repository.AuthRepository
import io.usdaves.auth.repository.SignUpResult
import io.usdaves.core.matcher.EmailMatcher
import io.usdaves.logger.Logger
import javax.inject.Inject

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

class SignUpUseCase @Inject constructor(
  private val repository: AuthRepository,
  private val emailMatcher: EmailMatcher,
  private val logger: Logger,
) {

  init {
    // Just to monitor lifetime of the dependencies
    logger.i("SignUpUseCase instance created")
  }

  suspend operator fun invoke(displayName: String, email: String, password: String): SignUpResult {
    logger.d("SignUpUseCase invoked with Email: $email, Password: $password")
    val invalidInputsResult = getInvalidInputResult(displayName, email, password)
    if (invalidInputsResult != null) {
      return invalidInputsResult
    }
    return repository.signUp(displayName, email, password)
  }

  private fun getInvalidInputResult(
    displayName: String,
    email: String,
    password: String,
  ): SignUpResult? = when {
    displayName.isEmpty() -> SignUpResult.EmptyDisplayName
    displayName.length < MIN_DISPLAY_NAME_LENGTH -> SignUpResult.TooShortDisplayName
    displayName.length > MAX_DISPLAY_NAME_LENGTH -> SignUpResult.TooLongDisplayName
    email.isEmpty() -> SignUpResult.EmptyEmail
    !emailMatcher.matches(email) -> SignUpResult.InvalidEmail
    password.isEmpty() -> SignUpResult.EmptyPassword
    password.length < MIN_PASSWORD_LENGTH -> SignUpResult.TooShortPassword
    password.length > MAX_PASSWORD_LENGTH -> SignUpResult.TooLongPassword
    else -> null
  }

  // If you want to change these values, consider changing also in the SignInUseCase file.
  companion object {
    const val MIN_DISPLAY_NAME_LENGTH = 2
    const val MAX_DISPLAY_NAME_LENGTH = 32
    const val MIN_PASSWORD_LENGTH = 8
    const val MAX_PASSWORD_LENGTH = 64
  }
}
