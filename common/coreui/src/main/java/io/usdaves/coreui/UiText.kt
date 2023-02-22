package io.usdaves.coreui

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

@Parcelize
sealed class UiText : Parcelable {

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
