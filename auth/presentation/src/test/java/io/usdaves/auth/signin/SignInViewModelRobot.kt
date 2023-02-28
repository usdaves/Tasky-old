package io.usdaves.auth.signin

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.fake.FakeSignInUseCase
import io.usdaves.auth.repository.SignInResult

// Created by usdaves(Usmon Abdurakhmanov) on 2/27/2023

internal class SignInViewModelRobot {

  private lateinit var viewModel: SignInViewModel
  private lateinit var signInUseCase: FakeSignInUseCase

  fun buildViewModel() = apply {
    signInUseCase = FakeSignInUseCase()
    viewModel = SignInViewModel(
      signInUseCase = signInUseCase.mock,
      savedStateHandle = SavedStateHandle(),
    )
  }

  fun mockLoginResultWithCredentials(
    email: String,
    password: String,
    result: SignInResult,
  ) = apply {
    signInUseCase.mockWithCredentialsForResult(email, password, result)
  }

  fun enterEmail(email: String) = apply {
    viewModel.onEmailChanged(email)
  }

  fun enterPassword(password: String) = apply {
    viewModel.onPasswordChanged(password)
  }

  fun clickSignInButton() = apply {
    viewModel.onSignInClicked()
  }

  fun clickSignUpButton() = apply {
    viewModel.onSignUpClicked()
  }

  suspend fun expectViewStates(
    vararg viewStates: SignInViewState,
    action: SignInViewModelRobot.() -> Unit,
  ) = apply {
    viewModel.viewState.test {
      action()
      for (viewState in viewStates) {
        assertThat(awaitItem()).isEqualTo(viewState)
      }
      cancelAndConsumeRemainingEvents()
    }
  }

  suspend fun expectViewEvents(
    vararg viewEvents: SignInViewEvent,
    action: SignInViewModelRobot.() -> Unit,
  ) = apply {
    viewModel.viewEvent.test {
      action()
      for (viewEvent in viewEvents) {
        assertThat(awaitItem()).isEqualTo(viewEvent)
      }
      cancelAndConsumeRemainingEvents()
    }
  }
}
