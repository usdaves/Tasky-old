package io.usdaves.auth.signin

import io.usdaves.core.UiText

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

sealed interface SignInViewEvent {
  object NavigateViewSignUp : SignInViewEvent
  data class ShowMessage(val message: UiText) : SignInViewEvent
}
