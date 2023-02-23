package io.usdaves.auth.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usdaves.auth.presentation.R
import io.usdaves.auth.repository.SignInResult
import io.usdaves.auth.signin.SignInViewEvent.NavigateViewSignUp
import io.usdaves.auth.signin.SignInViewEvent.ShowMessage
import io.usdaves.auth.usecase.SignInUseCase
import io.usdaves.core.TaskyDispatchers
import io.usdaves.core.UiText
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val VIEW_STATE = "sign_in_view_state_key"

@HiltViewModel
internal class SignInViewModel @Inject constructor(
  private val signInUseCase: SignInUseCase,
  private val dispatchers: TaskyDispatchers,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  val viewState = savedStateHandle.getStateFlow(VIEW_STATE, SignInViewState())

  private val viewEventChannel = Channel<SignInViewEvent>()
  val viewEvent = viewEventChannel.receiveAsFlow()

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

  fun onSignUpClicked() {
    viewEventChannel.trySend(NavigateViewSignUp)
  }

  fun onSignInClicked() {
    viewModelScope.launch(dispatchers.IO) {
      withLoadingViewState {
        val signInResult = signInUseCase(
          email = viewState.value.email,
          password = viewState.value.password,
        )
        processSignInResult(signInResult)
      }
    }
  }

  fun onContinueWithGoogleClicked() {
    viewEventChannel.trySend(ShowMessage(UiText(R.string.signing_with_google_not_supported)))
  }

  private fun processSignInResult(signInResult: SignInResult) {
    when (signInResult) {
      SignInResult.Success -> {
        savedStateHandle[VIEW_STATE] = SignInViewState()
      }

      SignInResult.EmptyEmail -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          emailError = UiText(R.string.empty_email),
        )
      }

      SignInResult.EmptyPassword -> {
        savedStateHandle[VIEW_STATE] = viewState.value.copy(
          passwordError = UiText(R.string.empty_password),
        )
      }

      SignInResult.InvalidCredentials -> {
        viewEventChannel.trySend(ShowMessage(UiText(R.string.invalid_credentials)))
      }

      SignInResult.NoNetworkConnection -> {
        viewEventChannel.trySend(ShowMessage(UiText(R.string.no_network_connection)))
      }

      is SignInResult.UnknownError -> {
        viewEventChannel.trySend(ShowMessage(UiText(signInResult.message)))
      }
    }
  }

  private inline fun withLoadingViewState(function: () -> Unit) {
    savedStateHandle[VIEW_STATE] = viewState.value.copy(isLoading = true)
    function()
    savedStateHandle[VIEW_STATE] = viewState.value.copy(isLoading = false)
  }
}


