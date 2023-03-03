package io.usdaves.onboarding.usecase

import io.usdaves.core.preferences.OnboardingPreferences
import io.usdaves.logger.Logger
import javax.inject.Inject

// Created by usdaves(Usmon Abdurakhmanov) on 3/3/2023

class GetOnboardingCompleteUseCase @Inject constructor(
  private val onboardingPreferences: OnboardingPreferences,
  logger: Logger,
) {

  // Just to see the lifetime of this class instance
  init {
    logger.i("GetOnboardingCompleteUseCase class instance created")
  }

  suspend operator fun invoke(isOnboardingCompleted: Boolean) {
    onboardingPreferences.setOnboardingCompleted(isOnboardingCompleted)
  }
}
