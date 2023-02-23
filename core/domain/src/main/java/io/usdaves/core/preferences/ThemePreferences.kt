package io.usdaves.core.preferences

import kotlinx.coroutines.flow.Flow

// Created by usdaves(Usmon Abdurakhmanov) on 2/4/2023

interface ThemePreferences {

  val themeFlow: Flow<Theme>

  suspend fun setTheme(theme: Theme)

  enum class Theme {
    LIGHT,
    DARK,
    SYSTEM,
  }
}
