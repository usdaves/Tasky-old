package io.usdaves.logger.internal

import android.os.Build
import java.util.regex.Pattern
import timber.log.Timber

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

internal class TimberDebugTree : Timber.DebugTree() {

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    super.log(priority, createClassTag(), message, t)
  }

  private fun createClassTag(): String {
    val stackTrace = Throwable().stackTrace
    check(stackTrace.size > CALL_STACK_INDEX) {
      "Synthetic stacktrace didn't have enough elements: are you using proguard?"
    }
    var tag = stackTrace[CALL_STACK_INDEX].className
    val m = ANONYMOUS_CLASS.matcher(tag)
    if (m.find()) {
      tag = m.replaceAll("")
    }
    tag = tag.substring(tag.lastIndexOf('.') + 1)
    // Tag length limit was removed in Android Nougat(API 24).
    return when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || tag.length <= MAX_TAG_LENGTH -> tag
      else -> tag.substring(0, MAX_TAG_LENGTH)
    }
  }

  private companion object {
    const val MAX_TAG_LENGTH = 23
    const val CALL_STACK_INDEX = 6
    val ANONYMOUS_CLASS: Pattern by lazy { Pattern.compile("(\\$\\d+)+$") }
  }
}
