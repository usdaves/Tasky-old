package io.usdaves.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.usdaves.onboarding.presentation.R
import io.usdaves.onboarding.presentation.databinding.FragmentOnboardingBinding

// Created by usdaves(Usmon Abdurakhmanov) on 3/1/2023

@AndroidEntryPoint
class OnboardingFragment :
  BindingFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

  private val viewModel: OnboardingViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.viewModel = viewModel
  }
}
