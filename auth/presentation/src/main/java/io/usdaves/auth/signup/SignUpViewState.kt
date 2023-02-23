package io.usdaves.auth.signup

import io.usdaves.core.UiText
import java.io.Serializable

data class SignUpViewState(
  val displayName: String = "",
  val displayNameError: UiText? = null,
  val email: String = "",
  val emailError: UiText? = null,
  val password: String = "",
  val passwordError: UiText? = null,
  val isLoading: Boolean = false,
) : Serializable
