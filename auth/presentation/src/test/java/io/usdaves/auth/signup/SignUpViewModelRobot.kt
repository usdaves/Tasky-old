package io.usdaves.auth.signup

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.fake.FakeSignUpUseCase
import io.usdaves.auth.repository.SignUpResult

// Created by usdaves(Usmon Abdurakhmanov) on 2/28/2023

class SignUpViewModelRobot {

  private lateinit var signUpUseCase: FakeSignUpUseCase
  private lateinit var viewModel: SignUpViewModel

  fun buildViewModel() = apply {
    signUpUseCase = FakeSignUpUseCase()
    viewModel = SignUpViewModel(
      signUpUseCase = signUpUseCase.mock,
      savedStateHandle = SavedStateHandle(),
    )
  }

  fun mockLoginResultWithCredentials(
    displayName: String,
    email: String,
    password: String,
    result: SignUpResult,
  ) = apply {
    signUpUseCase.mockWithCredentialsForResult(displayName, email, password, result)
  }

  fun enterDisplayName(displayName: String) = apply {
    viewModel.onDisplayNameChanged(displayName)
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
    vararg viewStates: SignUpViewState,
    action: SignUpViewModelRobot.() -> Unit,
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
    vararg viewEvents: SignUpViewEvent,
    action: SignUpViewModelRobot.() -> Unit,
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
