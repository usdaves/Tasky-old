package io.usdaves.core.preferences

import kotlinx.coroutines.flow.Flow

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

interface AuthPreferences {

  val isAuthenticated: Flow<Boolean>

  suspend fun setAuthenticated(isAuthenticated: Boolean = true)
}
