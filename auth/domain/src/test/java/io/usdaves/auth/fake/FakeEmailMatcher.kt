package io.usdaves.auth.fake

import io.mockk.every
import io.mockk.mockk
import io.usdaves.core.matcher.EmailMatcher

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

class FakeEmailMatcher {

  val mock: EmailMatcher = mockk()

  fun mockForResult(
    isValidEmail: Boolean,
  ) {
    every {
      mock.matches(any())
    } returns isValidEmail
  }
}
