package io.usdaves.auth.usecase

import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.repository.SignUpResult
import io.usdaves.test.FakeAuthRepository
import io.usdaves.test.FakeEmailMatcher
import io.usdaves.test.FakeLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

@OptIn(ExperimentalCoroutinesApi::class)
internal class SignUpUseCaseTest {

  private lateinit var signUpUseCase: SignUpUseCase
  private lateinit var emailMatcher: FakeEmailMatcher

  private val defaultDisplayName = "DisplayName"
  private val defaultEmail = "email@mail.com"
  private val defaultPassword = "password"

  @Before
  fun setUp() {
    emailMatcher = FakeEmailMatcher()
    signUpUseCase = SignUpUseCase(
      repository = FakeAuthRepository(),
      emailMatcher = emailMatcher,
      logger = FakeLogger(),
    )
  }

  @Test
  fun `Empty display name, Returns EmptyDisplayName result`() = runTest {
    val result = signUpUseCase(
      displayName = "",
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.EmptyDisplayName)
  }

  @Test
  fun `Empty email, Returns EmptyEmail result`() = runTest {
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = "",
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.EmptyEmail)
  }

  @Test
  fun `Empty password, Returns EmptyPassword result`() = runTest {
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = "",
    )
    assertThat(result).isEqualTo(SignUpResult.EmptyPassword)
  }

  @Test
  fun `Too short display name, Returns TooShortDisplayName result`() = runTest {
    val result = signUpUseCase(
      displayName = "a".repeat(SignUpUseCase.MIN_DISPLAY_NAME_LENGTH - 1),
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.TooShortDisplayName)
  }

  @Test
  fun `Too long display name, Returns TooShortDisplayName result`() = runTest {
    val result = signUpUseCase(
      displayName = "a".repeat(SignUpUseCase.MAX_DISPLAY_NAME_LENGTH + 1),
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.TooLongDisplayName)
  }

  @Test
  fun `Invalid email, Returns InvalidEmail result`() = runTest {
    emailMatcher.setValidationResult(isEmailValid = false)
    val result = signUpUseCase(
      displayName = "emailmailcom",
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.InvalidEmail)
  }

  @Test
  fun `Too short password, Returns TooShortPassword result`() = runTest {
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = "a".repeat(SignUpUseCase.MIN_PASSWORD_LENGTH - 1),
    )
    assertThat(result).isEqualTo(SignUpResult.TooShortPassword)
  }

  @Test
  fun `Too long password, Returns TooShortPassword result`() = runTest {
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = "a".repeat(SignUpUseCase.MAX_PASSWORD_LENGTH + 1),
    )
    assertThat(result).isEqualTo(SignUpResult.TooLongPassword)
  }

  @Test
  fun `Valid inputs, Returns Success result`() = runTest {
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.Success)
  }
}
