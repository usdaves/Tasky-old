package io.usdaves.onboarding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.usdaves.core.preferences.OnboardingPreferences
import io.usdaves.onboarding.presentation.R
import io.usdaves.onboarding.presentation.databinding.FragmentOnboardingBinding
import javax.inject.Inject
import kotlinx.coroutines.launch

// Created by usdaves(Usmon Abdurakhmanov) on 3/1/2023

@AndroidEntryPoint
class OnboardingFragment : BindingFragment<FragmentOnboardingBinding>(
  R.layout.fragment_onboarding,
) {

  @Inject
  lateinit var onboardingPreferences: OnboardingPreferences

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // TODO: Move this logic to an use case, and call from view model
    binding.completeOnboarding.setOnClickListener {
      lifecycleScope.launch {
        onboardingPreferences.setOnboardingCompleted()
      }
    }
  }
}
