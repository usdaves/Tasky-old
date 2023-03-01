package io.usdaves.tasky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usdaves.core.preferences.AuthPreferences
import io.usdaves.core.preferences.OnboardingPreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn

// Created by usdaves(Usmon Abdurakhmanov) on 2/28/2023

@HiltViewModel
class MainViewModel @Inject constructor(
  onboardingPreferences: OnboardingPreferences,
  authPreferences: AuthPreferences,
) : ViewModel() {

  val navDirection = combine(
    onboardingPreferences.isOnboardingCompleted,
    authPreferences.isAuthenticated,
  ) { isOnboardingCompleted, isAuthenticated ->
    when {
      isOnboardingCompleted && isAuthenticated -> NavDirection.NavigateToMain
      isOnboardingCompleted -> NavDirection.NavigateToAuth
      else -> NavDirection.NavigateToOnboarding
    }
  }
    .distinctUntilChanged()
    .shareIn(viewModelScope, SharingStarted.Eagerly)
}

