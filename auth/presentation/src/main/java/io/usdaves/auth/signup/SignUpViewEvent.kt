package io.usdaves.auth.signup

import io.usdaves.core.UiText

sealed interface SignUpViewEvent {
  object NavigateBackToSignIn : SignUpViewEvent
  data class ShowMessage(val message: UiText) : SignUpViewEvent
}
