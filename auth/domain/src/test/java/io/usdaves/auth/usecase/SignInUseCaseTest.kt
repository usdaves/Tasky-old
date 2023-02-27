package io.usdaves.auth.usecase

import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.fake.FakeAuthRepository
import io.usdaves.auth.repository.SignInResult
import io.usdaves.test.fake.FakeEmailMatcher
import io.usdaves.test.fake.FakeLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

@OptIn(ExperimentalCoroutinesApi::class)
class SignInUseCaseTest {

  private lateinit var authRepository: FakeAuthRepository
  private lateinit var emailMatcher: FakeEmailMatcher
  private lateinit var logger: FakeLogger
  private lateinit var useCase: SignInUseCase

  private val defaultEmail = "email@mail.com"
  private val defaultPassword = "password"

  @Before
  fun setUp() {
    authRepository = FakeAuthRepository()
    emailMatcher = FakeEmailMatcher()
    logger = FakeLogger()
    useCase = SignInUseCase(
      repository = authRepository.mock,
      emailMatcher = emailMatcher.mock,
      logger = logger.mock,
    )
  }

  @Test
  fun `Empty email, Returns EmptyEmail result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = false)
    val result = useCase(
      email = "",
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignInResult.EmptyEmail)
    authRepository.verifySignInNotCalled()
  }

  @Test
  fun `Empty password, Returns EmptyPassword result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = useCase(
      email = defaultEmail,
      password = "",
    )
    assertThat(result).isEqualTo(SignInResult.EmptyPassword)
    authRepository.verifySignInNotCalled()
  }

  @Test
  fun `Invalid email, Returns InvalidCredentials result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = false)
    val result = useCase(
      email = "huntermailme",
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignInResult.InvalidCredentials)
    authRepository.verifySignInNotCalled()
  }

  @Test
  fun `Too short password, Imminently returns InvalidCredentials result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = useCase(
      email = defaultEmail,
      password = "a".repeat(SignInUseCase.MIN_PASSWORD_LENGTH - 1),
    )
    assertThat(result).isEqualTo(SignInResult.InvalidCredentials)
    authRepository.verifySignInNotCalled()
  }

  @Test
  fun `Too long password, Imminently returns InvalidCredentials result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    val result = useCase(
      email = defaultEmail,
      password = "a".repeat(SignInUseCase.MAX_PASSWORD_LENGTH + 1),
    )
    assertThat(result).isEqualTo(SignInResult.InvalidCredentials)
    authRepository.verifySignInNotCalled()
  }

  @Test
  fun `Valid email and password, Returns Success result`() = runTest {
    emailMatcher.mockForResult(isValidEmail = true)
    authRepository.mockForSignInResult(SignInResult.Success)
    val result = useCase(
      email = defaultEmail,
      password = defaultPassword,
    )
    assertThat(result).isEqualTo(SignInResult.Success)
  }
}
