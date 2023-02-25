package io.usdaves.auth.signin

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.usdaves.auth.usecase.SignInUseCase
import io.usdaves.core.TaskyDispatchers
import io.usdaves.test.FakeAuthRepository
import io.usdaves.test.FakeEmailMatcher
import io.usdaves.test.FakeLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

@OptIn(ExperimentalCoroutinesApi::class)
internal class SignInViewModelTest {

  private lateinit var viewModel: SignInViewModel
  private lateinit var signInUseCase: SignInUseCase
  private lateinit var emailMatcher: FakeEmailMatcher
  private val testDispatchers = TaskyDispatchers(
    Main = UnconfinedTestDispatcher(),
    IO = UnconfinedTestDispatcher(),
    Default = UnconfinedTestDispatcher(),
    Unconfined = UnconfinedTestDispatcher(),
  )

  @Before
  fun setUp() {
    emailMatcher = FakeEmailMatcher()
    signInUseCase = SignInUseCase(
      repository = FakeAuthRepository(),
      emailMatcher = emailMatcher,
      logger = FakeLogger(),
    )
    viewModel = SignInViewModel(
      signInUseCase = signInUseCase,
      dispatchers = testDispatchers,
      savedStateHandle = SavedStateHandle(),
    )
  }

  @Test
  fun `Email entered, email field changed to the entered email`() = runTest {
    val email = "email"
    viewModel.onEmailChanged(email)
    assertThat(viewModel.viewState.value.email).isEqualTo(email)
  }

  @Test
  fun `Submitted with empty email, and then email entered, emailError is gone`() = runTest {
    viewModel.onSignInClicked()
    assertThat(viewModel.viewState.value.emailError).isNotNull()

    viewModel.onEmailChanged(email = "email")
    assertThat(viewModel.viewState.value.emailError).isNull()
  }
}
