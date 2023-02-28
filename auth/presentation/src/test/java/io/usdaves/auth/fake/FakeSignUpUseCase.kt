package io.usdaves.auth.fake

import io.mockk.coEvery
import io.mockk.mockk
import io.usdaves.auth.repository.SignUpResult
import io.usdaves.auth.usecase.SignUpUseCase

// Created by usdaves(Usmon Abdurakhmanov) on 2/28/2023

class FakeSignUpUseCase {

  val mock: SignUpUseCase = mockk()

  fun mockWithCredentialsForResult(
    displayName: String,
    email: String,
    password: String,
    result: SignUpResult,
  ) {
    coEvery {
      mock.invoke(displayName, email, password)
    } returns result
  }
}
