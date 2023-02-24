package io.usdaves.auth.usecase

import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.repository.SignInResult
import io.usdaves.test.FakeAuthRepository
import io.usdaves.test.FakeEmailMatcher
import io.usdaves.test.FakeLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

@OptIn(ExperimentalCoroutinesApi::class)
class SignInUseCaseTest {

  private lateinit var signInUseCase: SignInUseCase
  private lateinit var emailMatcher: FakeEmailMatcher

  private val defaultEmail = "email@mail.com"
  private val defaultPassword = "password"

  @Before
  fun setUp() {
    emailMatcher = FakeEmailMatcher()
    signInUseCase = SignInUseCase(
      repository = FakeAuthRepository(),
      emailMatcher = emailMatcher,
      logger = FakeLogger(),
    )
  }

  @Test
  fun `Empty email, Returns EmptyEmail result`() = runTest {
    val result = signInUseCase(
      email = "",
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignInResult.EmptyEmail)
  }

  @Test
  fun `Empty password, Returns EmptyPassword result`() = runTest {
    val result = signInUseCase(
      email = defaultEmail,
      password = "",
    )
    assertThat(result).isEqualTo(SignInResult.EmptyPassword)
  }

  @Test
  fun `Invalid email, Returns InvalidCredentials result`() = runTest {
    emailMatcher.setValidationResult(isEmailValid = false)
    val result = signInUseCase(
      email = "huntermailme",
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignInResult.InvalidCredentials)
  }

  @Test
  fun `Too short password, Imminently returns InvalidCredentials result`() = runTest {
    val result = signInUseCase(
      email = defaultEmail,
      password = "a".repeat(SignInUseCase.MIN_PASSWORD_LENGTH - 1),
    )
    assertThat(result).isEqualTo(SignInResult.InvalidCredentials)
  }

  @Test
  fun `Too long password, Imminently returns InvalidCredentials result`() = runTest {
    val result = signInUseCase(
      email = defaultEmail,
      password = "a".repeat(SignInUseCase.MAX_PASSWORD_LENGTH + 1),
    )
    assertThat(result).isEqualTo(SignInResult.InvalidCredentials)
  }

  @Test
  fun `Valid email and password, Returns Success result`() = runTest {
    val result = signInUseCase(
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignInResult.Success)
  }
}
