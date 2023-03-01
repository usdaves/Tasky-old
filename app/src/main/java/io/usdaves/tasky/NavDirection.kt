package io.usdaves.tasky

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes

sealed class NavDirection(
  @NavigationRes val graphId: Int,
  @IdRes val startDestination: Int? = null,
) {
  object NavigateToOnboarding : NavDirection(
    io.usdaves.onboarding.presentation.R.navigation.navigation_onboarding,
  )

  object NavigateToAuth : NavDirection(
    io.usdaves.auth.presentation.R.navigation.navigation_auth,
  )

  object NavigateToMain : NavDirection(R.navigation.navigation_main)
}
