package io.usdaves.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usdaves.onboarding.usecase.GetOnboardingCompleteUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
  private val onboardingCompleteUseCase: GetOnboardingCompleteUseCase,
) : ViewModel() {

  fun onOnboardingCompleteClicked() {
    viewModelScope.launch {
      onboardingCompleteUseCase(isOnboardingCompleted = true)
    }
  }
}
