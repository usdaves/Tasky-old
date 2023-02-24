package io.usdaves.auth.fake

import io.usdaves.core.matcher.EmailMatcher

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

class FakeEmailMatcher : EmailMatcher {

  private var validationResult = true

  override fun matches(emailAddress: CharSequence): Boolean = validationResult

  fun setValidationResult(isEmailValid: Boolean) {
    validationResult = isEmailValid
  }
}
