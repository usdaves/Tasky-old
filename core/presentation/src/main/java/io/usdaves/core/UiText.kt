package io.usdaves.core

import android.content.Context
import androidx.annotation.StringRes
import java.io.Serializable

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

sealed class UiText : Serializable {

  data class StringResource internal constructor(@StringRes val resId: Int) : UiText()
  data class StringValue internal constructor(val value: String) : UiText()

  fun asString(context: Context): String = when (this) {
    is StringValue -> value
    is StringResource -> context.getString(resId)
  }

  companion object {
    operator fun invoke(@StringRes resId: Int): UiText = StringResource(resId)
    operator fun invoke(value: String): UiText = StringValue(value)
  }
}
