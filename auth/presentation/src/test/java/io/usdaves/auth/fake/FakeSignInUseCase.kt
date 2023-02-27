package io.usdaves.auth.fake

import io.mockk.coEvery
import io.mockk.mockk
import io.usdaves.auth.repository.SignInResult
import io.usdaves.auth.usecase.SignInUseCase

// Created by usdaves(Usmon Abdurakhmanov) on 2/27/2023

class FakeSignInUseCase {

  val mock: SignInUseCase = mockk()

  fun mockInvokeForResult(
    result: SignInResult,
  ) {
    coEvery {
      mock.invoke(any(), any())
    } returns result
  }
}
