package io.usdaves.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.usdaves.auth.presentation.R
import io.usdaves.auth.presentation.databinding.FragmentSignInBinding
import io.usdaves.core.util.collectLatest

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

@AndroidEntryPoint
class SignInFragment : BindingFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

  private val viewModel: SignInViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel

    collectLatest(viewModel.viewEvent) { event ->
      when (event) {
        SignInViewEvent.NavigateViewSignUp -> {
          Toast.makeText(requireContext(), "Navigate to SignUp event", Toast.LENGTH_SHORT).show()
        }

        is SignInViewEvent.ShowMessage -> {
          Toast.makeText(
            requireContext(),
            event.message.asString(requireContext()),
            Toast.LENGTH_SHORT,
          ).show()
        }
      }
    }
  }
}
