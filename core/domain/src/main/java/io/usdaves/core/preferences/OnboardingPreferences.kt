package io.usdaves.core.preferences

import kotlinx.coroutines.flow.Flow

// Created by usdaves(Usmon Abdurakhmanov) on 3/1/2023

interface OnboardingPreferences {

  val isOnboardingCompleted: Flow<Boolean>

  suspend fun setOnboardingCompleted(isOnboardingCompleted: Boolean)
}
