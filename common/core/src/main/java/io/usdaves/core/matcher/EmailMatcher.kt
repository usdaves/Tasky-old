package io.usdaves.core.matcher

// Created by usdaves(Usmon Abdurakhmanov) on 2/4/2023

interface EmailMatcher {

  fun matches(emailAddress: CharSequence): Boolean
}
