package io.usdaves.auth.signin

import io.usdaves.core.UiText
import java.io.Serializable

data class SignInViewState(
  val email: String = "",
  val emailError: UiText? = null,
  val password: String = "",
  val passwordError: UiText? = null,
  val isLoading: Boolean = false,
) : Serializable
