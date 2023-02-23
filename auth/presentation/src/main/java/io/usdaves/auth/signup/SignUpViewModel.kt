package io.usdaves.auth.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usdaves.auth.presentation.R
import io.usdaves.auth.repository.SignUpResult
import io.usdaves.auth.signup.SignUpViewEvent.NavigateBackToSignIn
import io.usdaves.auth.signup.SignUpViewEvent.ShowMessage
import io.usdaves.auth.usecase.SignUpUseCase
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MAX_DISPLAY_NAME_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MAX_PASSWORD_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MIN_DISPLAY_NAME_LENGTH
import io.usdaves.auth.usecase.SignUpUseCase.Companion.MIN_PASSWORD_LENGTH
import io.usdaves.core.TaskyDispatchers
import io.usdaves.core.UiText
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val VIEW_STATE = "sign_up_view_state_key"

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val signUpUseCase: SignUpUseCase,
  private val dispatchers: TaskyDispatchers,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  val viewState = savedStateHandle.getStateFlow(VIEW_STATE, SignUpViewState())

  private val viewEventChannel = Channel<SignUpViewEvent>()
  val viewEvent = viewEventChannel.receiveAsFlow()

  fun onDisplayNameChanged(displayName: String) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(
      displayName = displayName,
      displayNameError = null,
    )
  }

  fun onEmailChanged(email: String) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(
      email = email,
      emailError = null,
    )
  }

  fun onPasswordChanged(password: String) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(
      password = password,
      passwordError = null,
    )
  }

  fun onSignInClicked() {
    viewEventChannel.trySend(NavigateBackToSignIn)
  }

  fun onSignUpClicked() {
    viewModelScope.launch(dispatchers.IO) {
      withLoadingViewState {
        val signUpResult = signUpUseCase(
          displayName = viewState.value.displayName,
          email = viewState.value.email,
          password = viewState.value.password,
        )
        processSignUpResult(signUpResult)
      }
    }
  }

  fun onContinueWithGoogleClicked() {
    viewEventChannel.trySend(ShowMessage(UiText(R.string.signing_with_google_not_supported)))
  }

  private fun processSignUpResult(signUpResult: SignUpResult) {
    when (signUpResult) {
      SignUpResult.Success -> {
        savedStateHandle[VIEW_STATE] = SignUpViewState()
      }

      SignUpResult.EmptyDisplayName -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          displayNameError = UiText(R.string.empty_display_name),
        )
      }

      SignUpResult.EmptyEmail -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          emailError = UiText(R.string.empty_email),
        )
      }

      SignUpResult.EmptyPassword -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          passwordError = UiText(R.string.empty_password),
        )
      }

      SignUpResult.TooShortDisplayName -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          displayNameError = UiText(R.string.short_display_name, MIN_DISPLAY_NAME_LENGTH),
        )
      }

      SignUpResult.TooLongDisplayName -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          displayNameError = UiText(R.string.long_display_name, MAX_DISPLAY_NAME_LENGTH),
        )
      }

      SignUpResult.InvalidEmail -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          emailError = UiText(R.string.invalid_email),
        )
      }

      SignUpResult.EmailAlreadyInUse -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          emailError = UiText(R.string.email_in_use),
        )
      }

      SignUpResult.TooShortPassword -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          passwordError = UiText(R.string.short_password, MIN_PASSWORD_LENGTH),
        )
      }

      SignUpResult.TooLongPassword -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          passwordError = UiText(R.string.long_password, MAX_PASSWORD_LENGTH),
        )
      }

      SignUpResult.NoNetworkConnection -> {
        viewEventChannel.trySend(ShowMessage(UiText(R.string.no_network_connection)))
      }

      is SignUpResult.UnknownError -> {
        viewEventChannel.trySend(ShowMessage(UiText(signUpResult.message)))
      }
    }
  }

  private inline fun withLoadingViewState(function: () -> Unit) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(isLoading = true)
    function()
    savedStateHandle[VIEW_STATE] = viewState.value.copy(isLoading = false)
  }
}
