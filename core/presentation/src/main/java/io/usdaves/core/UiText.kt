package io.usdaves.core

import android.content.Context
import androidx.annotation.StringRes
import java.io.Serializable

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

sealed class UiText : Serializable {

  private data class Resource(@StringRes val resId: Int, val args: Array<out Any>) : UiText() {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false
      if (other !is Resource) return false
      if (resId != other.resId) return false
      return args.contentEquals(other.args)
    }

    override fun hashCode(): Int {
      return 31 * resId + args.contentHashCode()
    }
  }

  private data class Value(val value: String) : UiText()

  fun asString(context: Context): String = when (this) {
    is Value -> value
    is Resource -> context.getString(resId, args)
  }

  companion object {
    operator fun invoke(@StringRes resId: Int, vararg args: Any): UiText = Resource(resId, args)
    operator fun invoke(value: String): UiText = Value(value)
  }
}
