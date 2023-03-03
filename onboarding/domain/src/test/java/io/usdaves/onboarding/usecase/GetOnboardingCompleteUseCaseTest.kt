package io.usdaves.onboarding.usecase

import io.usdaves.onboarding.fake.FakeOnboardingPreferences
import io.usdaves.test.fake.FakeLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Created by usdaves(Usmon Abdurakhmanov) on 3/3/2023

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetOnboardingCompleteUseCaseTest {


  private lateinit var onboardingCompleteUseCase: GetOnboardingCompleteUseCase

  private lateinit var fakeOnboardingPreferences: FakeOnboardingPreferences
  private lateinit var fakeLogger: FakeLogger

  @Before
  fun setup() {
    fakeOnboardingPreferences = FakeOnboardingPreferences()
    fakeLogger = FakeLogger()
    onboardingCompleteUseCase = GetOnboardingCompleteUseCase(
      onboardingPreferences = fakeOnboardingPreferences.mock,
      logger = fakeLogger.mock,
    )
  }

  @Test
  fun `invoke(Boolean) function called, OnboardingPreferences#setOnboardingCompleted(Boolean) called exact same value`() =
    runTest {
      val isOnboardingCompleted = true
      onboardingCompleteUseCase(isOnboardingCompleted)
      fakeOnboardingPreferences.verifySetOnboardingCompleteInvokedForResult(isOnboardingCompleted)
    }
}
