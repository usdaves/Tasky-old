package io.usdaves.core

import android.content.Context
import androidx.annotation.StringRes
import java.io.Serializable

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

sealed class UiText : Serializable {
  private class Resource(@StringRes val resId: Int, vararg val args: Any) : UiText()
  private class Value(val value: String) : UiText()

  fun asString(context: Context): String = when (this) {
    is Value -> value
    is Resource -> context.getString(resId, args)
  }

  companion object {
    operator fun invoke(@StringRes resId: Int, vararg args: Any): UiText = Resource(resId, args)
    operator fun invoke(value: String): UiText = Value(value)
  }
}
