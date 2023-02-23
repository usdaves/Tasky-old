package io.usdaves.auth.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import io.usdaves.auth.remote.AuthApi
import io.usdaves.core.Result
import io.usdaves.core.preferences.AuthPreferences
import io.usdaves.core.repository.ProfileRepository
import io.usdaves.core.util.isSuccess
import io.usdaves.core.util.map
import io.usdaves.core.util.onEach
import io.usdaves.logger.Logger

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

internal class ProdAuthRepository(
  private val authApi: AuthApi,
  private val profileRepository: ProfileRepository,
  private val authPreferences: AuthPreferences,
  private val logger: Logger,
) : AuthRepository {

  init {
    // Just to monitor lifetime of the dependencies
    logger.i("ProdAuthRepository instance created")
  }

  override suspend fun signIn(email: String, password: String): SignInResult {
    logger.d("SignIn function invoked with Email: $email, Password: $password")
    val signInResult = authApi
      .signIn(email, password)
      .map { userId ->
        profileRepository.fetchUserProfileById(userId).onEach {
          authPreferences.setAuthenticated(isAuthenticated = isSuccess)
        }
      }
    return when (signInResult) {
      is Result.Success -> SignInResult.Success
      is Result.Failure -> processSignInError(signInResult.throwable)
    }
  }

  override suspend fun signUp(displayName: String, email: String, password: String): SignUpResult {
    logger.d("SignUp function invoked with DisplayName: $displayName, Email: $email, Password: $password")
    val signUpResult = authApi
      .signUp(email, password)
      .map { userId ->
        profileRepository.setUserProfile(userId, displayName, email).onEach {
          authPreferences.setAuthenticated(isAuthenticated = isSuccess)
        }
      }
    return when (signUpResult) {
      is Result.Success -> SignUpResult.Success
      is Result.Failure -> processSignUpError(signUpResult.throwable)
    }
  }

  private fun processSignInError(throwable: Throwable): SignInResult = when (throwable) {
    is FirebaseNetworkException -> SignInResult.NoNetworkConnection
    is FirebaseAuthException -> {
      when (throwable.errorCode) {
        "ERROR_USER_NOT_FOUND" -> SignInResult.InvalidCredentials
        else -> SignInResult.UnknownError("Error occurred while using FirebaseAuth: ${throwable.errorCode}")
      }
    }

    is FirebaseFirestoreException -> SignInResult.UnknownError("Error occurred while using FirebaseFirestore: ${throwable.code.name}")
    else -> SignInResult.UnknownError(throwable.localizedMessage ?: "Unknown error occurred")
  }

  private fun processSignUpError(throwable: Throwable): SignUpResult = when (throwable) {
    is FirebaseNetworkException -> SignUpResult.NoNetworkConnection
    is FirebaseAuthException -> {
      when (throwable.errorCode) {
        "ERROR_EMAIL_ALREADY_IN_USE" -> SignUpResult.EmailAlreadyInUse
        else -> SignUpResult.UnknownError("Error occurred while using FirebaseAuth: ${throwable.errorCode}")
      }
    }

    is FirebaseFirestoreException -> SignUpResult.UnknownError("Error occurred while using FirebaseFirestore: ${throwable.code.name}")
    else -> SignUpResult.UnknownError(throwable.localizedMessage ?: "Unknown error occurred")
  }
}
