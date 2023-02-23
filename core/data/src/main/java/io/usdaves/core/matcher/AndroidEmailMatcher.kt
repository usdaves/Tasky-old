package io.usdaves.core.matcher

import android.util.Patterns
import io.usdaves.logger.Logger

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

internal class AndroidEmailMatcher(
  logger: Logger,
) : EmailMatcher {

  init {
    // Just to monitor lifetime of the dependencies, because they're not singletons
    logger.i("AndroidEmailMatcher instance created")
  }

  override fun matches(emailAddress: CharSequence): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
  }
}
