package io.usdaves.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import io.usdaves.auth.presentation.R
import io.usdaves.auth.presentation.databinding.FragmentSignUpBinding
import io.usdaves.core.util.collectLatest

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

class SignUpFragment : BindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

  private val viewModel: SignUpViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel

    collectLatest(viewModel.viewEvent) { event ->
      when (event) {
        SignUpViewEvent.NavigateBackToSignIn -> {
          Toast.makeText(requireContext(), "Back to SignIn event", Toast.LENGTH_SHORT).show()
        }

        is SignUpViewEvent.ShowMessage -> {
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
