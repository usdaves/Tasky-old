package io.usdaves.core.internal.matcher

import android.util.Patterns
import io.usdaves.core.domain.matcher.EmailMatcher

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

internal class AndroidEmailMatcher : EmailMatcher {

  override fun matches(emailAddress: CharSequence): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
  }
}
