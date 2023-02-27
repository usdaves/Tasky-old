package io.usdaves.auth.fake

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.usdaves.auth.repository.AuthRepository
import io.usdaves.auth.repository.SignInResult
import io.usdaves.auth.repository.SignUpResult

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

class FakeAuthRepository {

  val mock: AuthRepository = mockk()

  fun mockForSignInResult(
    result: SignInResult,
  ) {
    coEvery {
      mock.signIn(any(), any())
    } returns result
  }

  fun verifySignInNotCalled() {
    coVerify(exactly = 0) {
      mock.signIn(any(), any())
    }
  }

  fun mockForSignUpResult(
    result: SignUpResult,
  ) {
    coEvery {
      mock.signUp(any(), any(), any())
    } returns result
  }

  fun verifySignUpNotCalled() {
    coVerify(exactly = 0) {
      mock.signUp(any(), any(), any())
    }
  }
}
