package io.usdaves.core

import android.app.Activity
import android.view.View

fun interface ActivityContentSetter {
  fun setContentView(activity: Activity, view: View)
}
