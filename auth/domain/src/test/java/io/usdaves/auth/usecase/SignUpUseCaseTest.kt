package io.usdaves.auth.usecase

import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.fake.FakeAuthRepository
import io.usdaves.auth.repository.SignUpResult
import io.usdaves.test.fake.FakeEmailMatcher
import io.usdaves.test.fake.FakeLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

@OptIn(ExperimentalCoroutinesApi::class)
internal class SignUpUseCaseTest {

  private lateinit var authRepository: FakeAuthRepository
  private lateinit var emailMatcher: FakeEmailMatcher
  private lateinit var logger: FakeLogger
  private lateinit var signUpUseCase: SignUpUseCase

  private val defaultDisplayName = "DisplayName"
  private val defaultEmail = "email@mail.com"
  private val defaultPassword = "password"

  @Before
  fun setUp() {
    authRepository = FakeAuthRepository()
    emailMatcher = FakeEmailMatcher()
    logger = FakeLogger()
    signUpUseCase = SignUpUseCase(
      repository = authRepository.mock,
      emailMatcher = emailMatcher.mock,
      logger = logger.mock,
    )
  }

  @Test
  fun `Empty display name, Returns EmptyDisplayName result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = signUpUseCase(
      displayName = "",
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.EmptyDisplayName)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Empty email, Returns EmptyEmail result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = false)
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = "",
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.EmptyEmail)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Empty password, Returns EmptyPassword result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = "",
    )
    assertThat(result).isEqualTo(SignUpResult.EmptyPassword)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Too short display name, Returns TooShortDisplayName result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = signUpUseCase(
      displayName = "a".repeat(SignUpUseCase.MIN_DISPLAY_NAME_LENGTH - 1),
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.TooShortDisplayName)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Too long display name, Returns TooShortDisplayName result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = signUpUseCase(
      displayName = "a".repeat(SignUpUseCase.MAX_DISPLAY_NAME_LENGTH + 1),
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.TooLongDisplayName)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Invalid email, Returns InvalidEmail result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = false)
    val result = signUpUseCase(
      displayName = "emailmailcom",
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.InvalidEmail)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Too short password, Returns TooShortPassword result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = "a".repeat(SignUpUseCase.MIN_PASSWORD_LENGTH - 1),
    )
    assertThat(result).isEqualTo(SignUpResult.TooShortPassword)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Too long password, Returns TooShortPassword result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = "a".repeat(SignUpUseCase.MAX_PASSWORD_LENGTH + 1),
    )
    assertThat(result).isEqualTo(SignUpResult.TooLongPassword)
    authRepository.verifySignUpNotCalled()
  }

  @Test
  fun `Valid inputs, Returns Success result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    authRepository.mockForSignUpResult(SignUpResult.Success)
    val result = signUpUseCase(
      displayName = defaultDisplayName,
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignUpResult.Success)
  }
}
