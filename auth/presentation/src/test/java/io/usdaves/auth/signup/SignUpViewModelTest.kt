package io.usdaves.auth.signup

import io.usdaves.auth.presentation.R
import io.usdaves.auth.repository.SignUpResult
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MAX_DISPLAY_NAME_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MAX_PASSWORD_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MIN_DISPLAY_NAME_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MIN_PASSWORD_LENGTH
import io.usdaves.core.UiText
import io.usdaves.test.fake.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/28/2023

@OptIn(ExperimentalCoroutinesApi::class)
internal class SignUpViewModelTest {

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  private lateinit var viewModelRobot: SignUpViewModelRobot

  private val defaultDisplayName = "DisplayName"
  private val defaultEmail = "example@gmail.com"
  private val defaultPassword = "12345678"

  @Before
  fun setup() {
    viewModelRobot = SignUpViewModelRobot()
  }

  @Test
  fun `DisplayName, Email and password entered, View state stores exact same displayName, email and password`() =
    runTest {
      val initialState = SignUpViewState()
      val displayNameEnteredState = initialState.copy(
        displayName = defaultDisplayName,
      )
      val emailEnteredState = displayNameEnteredState.copy(
        email = defaultEmail,
      )
      val passwordEnteredState = emailEnteredState.copy(
        password = defaultPassword,
      )
      viewModelRobot
        .buildViewModel()
        .expectViewStates(
          initialState,
          displayNameEnteredState,
          emailEnteredState,
          passwordEnteredState,
        ) {
          enterDisplayName(defaultDisplayName)
          enterEmail(defaultEmail)
          enterPassword(defaultPassword)
        }
    }

  @Test
  fun `Submitted with empty DisplayName, Empty display name error occurs`() = runTest {
    val initialState = SignUpViewState()
    val emailEnteredState = initialState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithDisplayNameError = submittingWithLoadingState.copy(
      displayNameError = UiText(R.string.empty_display_name),
    )
    val stateWithDisplayNameErrorWithoutLoading = stateWithDisplayNameError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = "",
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.EmptyDisplayName,
      ).expectViewStates(
        initialState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithDisplayNameError,
        stateWithDisplayNameErrorWithoutLoading,
      ) {
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with empty Email, Empty email error occurs`() = runTest {
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val passwordEnteredState = displayNameEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithEmailError = submittingWithLoadingState.copy(
      emailError = UiText(R.string.empty_email),
    )
    val stateWithEmailErrorWithoutLoading = stateWithEmailError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = "",
        password = defaultPassword,
        result = SignUpResult.EmptyEmail,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithEmailError,
        stateWithEmailErrorWithoutLoading,
      ) {
        enterDisplayName(defaultDisplayName)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with empty password, Empty password error occurs`() = runTest {
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
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
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = "",
        result = SignUpResult.EmptyPassword,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        submittingWithLoadingState,
        stateWithPasswordError,
        stateWithPasswordErrorWithoutLoading,
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with short display name, Too short display name error occurs`() = runTest {
    val shortDisplayName = "a".repeat(MIN_DISPLAY_NAME_LENGTH - 1)
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = shortDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithTooShortDisplayNameError = submittingWithLoadingState.copy(
      displayNameError = UiText(R.string.short_display_name, MIN_DISPLAY_NAME_LENGTH),
    )
    val stateWithTooShortDisplayNameErrorWithoutLoading = stateWithTooShortDisplayNameError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = shortDisplayName,
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.TooShortDisplayName,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithTooShortDisplayNameError,
        stateWithTooShortDisplayNameErrorWithoutLoading,
      ) {
        enterDisplayName(shortDisplayName)
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with long display name, Too long display name error occurs`() = runTest {
    val longDisplayName = "a".repeat(MAX_DISPLAY_NAME_LENGTH + 1)
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = longDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithTooLongDisplayNameError = submittingWithLoadingState.copy(
      displayNameError = UiText(R.string.long_display_name, MAX_DISPLAY_NAME_LENGTH),
    )
    val stateWithTooLongDisplayNameErrorWithoutLoading = stateWithTooLongDisplayNameError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = longDisplayName,
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.TooLongDisplayName,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithTooLongDisplayNameError,
        stateWithTooLongDisplayNameErrorWithoutLoading,
      ) {
        enterDisplayName(longDisplayName)
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with invalid email, InvalidEmail error occurs`() = runTest {
    val invalidEmail = "emailmailcom"
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = invalidEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithInvalidEmailError = submittingWithLoadingState.copy(
      emailError = UiText(R.string.invalid_email),
    )
    val stateWithInvalidEmailErrorWithoutLoading = stateWithInvalidEmailError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = invalidEmail,
        password = defaultPassword,
        result = SignUpResult.InvalidEmail,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithInvalidEmailError,
        stateWithInvalidEmailErrorWithoutLoading,
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(invalidEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with short password, Too short password error occurs`() = runTest {
    val shortPassword = "a".repeat(MIN_PASSWORD_LENGTH - 1)
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = shortPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithTooShortPasswordError = submittingWithLoadingState.copy(
      passwordError = UiText(R.string.short_password, MIN_PASSWORD_LENGTH),
    )
    val stateWithTooShortPasswordErrorWithoutLoading = stateWithTooShortPasswordError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = shortPassword,
        result = SignUpResult.TooShortPassword,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithTooShortPasswordError,
        stateWithTooShortPasswordErrorWithoutLoading,
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        enterPassword(shortPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with long password, Too long password error occurs`() = runTest {
    val longPassword = "a".repeat(MAX_PASSWORD_LENGTH + 1)
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = longPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithTooLongPasswordError = submittingWithLoadingState.copy(
      passwordError = UiText(R.string.long_password, MAX_PASSWORD_LENGTH),
    )
    val stateWithTooLongPasswordErrorWithoutLoading = stateWithTooLongPasswordError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = longPassword,
        result = SignUpResult.TooLongPassword,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithTooLongPasswordError,
        stateWithTooLongPasswordErrorWithoutLoading,
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        enterPassword(longPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with registered email, EmailAlreadyInUse error occurs`() = runTest {
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    val stateWithEmailAlreadyInUseError = submittingWithLoadingState.copy(
      emailError = UiText(R.string.email_in_use),
    )
    val stateWithEmailAlreadyInUseErrorWithoutLoading = stateWithEmailAlreadyInUseError.copy(
      isLoading = false,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.EmailAlreadyInUse,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        stateWithEmailAlreadyInUseError,
        stateWithEmailAlreadyInUseErrorWithoutLoading,
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with valid credentials, View state cleared`() = runTest {
    val initialState = SignUpViewState()
    val displayNameEnteredState = initialState.copy(
      displayName = defaultDisplayName,
    )
    val emailEnteredState = displayNameEnteredState.copy(
      email = defaultEmail,
    )
    val passwordEnteredState = emailEnteredState.copy(
      password = defaultPassword,
    )
    val submittingWithLoadingState = passwordEnteredState.copy(
      isLoading = true,
    )
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.Success,
      ).expectViewStates(
        initialState,
        displayNameEnteredState,
        emailEnteredState,
        passwordEnteredState,
        submittingWithLoadingState,
        initialState,
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with valid credentials, Unknown error occurs`() = runTest {
    val errorMessage = "unknown"
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.UnknownError(errorMessage),
      ).expectViewEvents(
        SignUpViewEvent.ShowMessage(UiText(errorMessage)),
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Submitted with valid credentials, NoNetworkConnection error occurs`() = runTest {
    viewModelRobot
      .buildViewModel()
      .mockLoginResultWithCredentials(
        displayName = defaultDisplayName,
        email = defaultEmail,
        password = defaultPassword,
        result = SignUpResult.NoNetworkConnection,
      ).expectViewEvents(
        SignUpViewEvent.ShowMessage(UiText(R.string.no_network_connection)),
      ) {
        enterDisplayName(defaultDisplayName)
        enterEmail(defaultEmail)
        enterPassword(defaultPassword)
        clickSignUpButton()
      }
  }

  @Test
  fun `Sign In button clicked, NavigateToSignIn event happens`() = runTest {
    viewModelRobot
      .buildViewModel()
      .expectViewEvents(
        SignUpViewEvent.NavigateBackToSignIn,
      ) {
        clickSignInButton()
      }
  }
}
