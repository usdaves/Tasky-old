package io.usdaves.auth.signin

import io.usdaves.auth.presentation.R
import io.usdaves.auth.repository.SignInResult
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MAX_PASSWORD_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MIN_PASSWORD_LENGTH
import io.usdaves.core.UiText
import io.usdaves.test.fake.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/27/2023

@OptIn(ExperimentalCoroutinesApi::class)
internal class SignInViewModelTest {

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  private lateinit var viewModelRobot: SignInViewModelRobot

  private val defaultEmail = "example@gmail.com"
  private val defaultPassword = "12345678"

  @Before
  fun setup() {
    viewModelRobot = SignInViewModelRobot()
  }

  @Test
  fun `Email and password entered, View state stores exact same email and password`() = runTest {
    val initialState = SignInViewState()
    val emailEnteredState = initialState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    viewModelRobot
      .buildViewModel()
      .expectViewStates(
        initialState, emailEnteredState, passwordEnteredState,
      ) {
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
      }
  }

  @Test
  fun `Submitted with empty email, Empty email error occurs`() = runTest {
    val initialState = SignInViewState()
    val submittingWithLoadingState = initialState.copy(
      isLoading = true,
    )
    val stateWithEmailError = submittingWithLoadingState.copy(
      emailError = UiText(R.string.empty_email),
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = "",
        password = "",
        result = SignInResult.EmptyEmail,
      ).expectViewStates(
        initialState, submittingWithLoadingState, stateWithEmailError,
      ) {
        clickSignInButton()
      }
  }

  @Test
  fun `Submitted with empty password, Empty password error occurs`() = runTest {
    val initialState = SignInViewState()
    val emailEnteredState = initialState.copy(
      email = defaultEmail,
    )
    val submittingWithLoadingState = emailEnteredState.copy(
      isLoading = true,
    )
    val stateWithPasswordError = submittingWithLoadingState.copy(
      passwordError = UiText(R.string.empty_password),
    )
    val stateWithPasswordErrorWithoutLoading = stateWithPasswordError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = defaultEmail,
        password = "",
        result = SignInResult.EmptyPassword,
      ).expectViewStates(
        initialState,
        emailEnteredState,
        submittingWithLoadingState,
        stateWithPasswordError,
        stateWithPasswordErrorWithoutLoading,
      ) {
        enterEmail(defaultEmail)
        clickSignInButton()
      }
  }

  @Test
  fun `Submitted with invalid email, Invalid credentials error occurs`() = runTest {
    val invalidEmail = "emailmailcom"
    val viewEventWithInvalidCredentialsMessage = SignInViewEvent.ShowMessage(
      message = UiText(R.string.invalid_credentials),
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = invalidEmail,
        password = defaultPassword,
        result = SignInResult.InvalidCredentials,
      ).expectViewEvents(
        viewEventWithInvalidCredentialsMessage,
      ) {
        enterEmail(invalidEmail)
        enterPassword(defaultPassword)
        clickSignInButton()
      }
  }

  @Test
  fun `Submitted with short password, Invalid credentials error occurs`() = runTest {
    val shortPassword = "a".repeat(MIN_PASSWORD_LENGTH - 1)
    val viewEventWithInvalidCredentialsMessage = SignInViewEvent.ShowMessage(
      message = UiText(R.string.invalid_credentials),
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = defaultEmail,
        password = shortPassword,
        result = SignInResult.InvalidCredentials,
      ).expectViewEvents(
        viewEventWithInvalidCredentialsMessage,
      ) {
        enterEmail(defaultEmail)
        enterPassword(shortPassword)
        clickSignInButton()
      }
  }

  @Test
  fun `Submitted with long password, Invalid credentials error occurs`() = runTest {
    val longPassword = "a".repeat(MAX_PASSWORD_LENGTH + 1)
    val viewEventWithInvalidCredentialsMessage = SignInViewEvent.ShowMessage(
      message = UiText(R.string.invalid_credentials),
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = defaultEmail,
        password = longPassword,
        result = SignInResult.InvalidCredentials,
      ).expectViewEvents(
        viewEventWithInvalidCredentialsMessage,
      ) {
        enterEmail(defaultEmail)
        enterPassword(longPassword)
        clickSignInButton()
      }
  }

  @Test
  fun `Submitted with valid credentials, Unknown error occurs`() = runTest {
    val errorMessage = "Unknown"
    val viewEventWithUnknownError = SignInViewEvent.ShowMessage(
      message = UiText(errorMessage),
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = defaultEmail,
        password = defaultPassword,
        result = SignInResult.UnknownError(errorMessage),
      ).expectViewEvents(
        viewEventWithUnknownError,
      ) {
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignInButton()
      }
  }

  @Test
  fun `Submitted with valid credentials, View state cleared`() = runTest {
    val initialState = SignInViewState()
    val emailEnteredState = initialState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )

    val submittingState = passwordEnteredState.copy(
      isLoading = true,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        email = defaultEmail,
        password = defaultPassword,
        result = SignInResult.Success,
      ).expectViewStates(
        initialState,
        emailEnteredState,
        passwordEnteredState,
        submittingState,
        initialState,
      ) {
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignInButton()
      }
  }

  @Test
  fun `SignUp clicked, NavigateToSignUp event occurs`() = runTest {
    val viewEventWithNavigateToSignUpEvent = SignInViewEvent.NavigateViewSignUp
    viewModelRobot
      .buildViewModel()
      .expectViewEvents(
        viewEventWithNavigateToSignUpEvent,
      ) {
        clickSignUpButton()
      }
  }
}
