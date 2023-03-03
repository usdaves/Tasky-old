package io.usdaves.onboarding.fake

import io.mockk.coVerify
import io.mockk.spyk
import io.usdaves.core.preferences.OnboardingPreferences

// Created by usdaves(Usmon Abdurakhmanov) on 3/3/2023

class FakeOnboardingPreferences {

  val mock: OnboardingPreferences = spyk()

  fun verifySetOnboardingCompleteInvokedForResult(
    isOnboardingCompleted: Boolean,
  ) {
    coVerify(exactly = 1) {
      mock.setOnboardingCompleted(isOnboardingCompleted)
    }
  }
}
