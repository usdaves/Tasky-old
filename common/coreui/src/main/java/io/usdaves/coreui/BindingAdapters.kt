package io.usdaves.coreui

import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

@BindingAdapter("onTextChanged")
fun EditText.onTextChanged(listener: OnTextChangeListener) {
  doOnTextChanged { text, _, _, _ ->
    listener.onTextChanged(text.toString())
  }
}

fun interface OnTextChangeListener {
  fun onTextChanged(text: String)
}

@BindingAdapter("error")
fun TextInputLayout.setError(error: UiText?) {
  isErrorEnabled = error != null
  setError(error?.asString(context))
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
  visibility = if (isVisible) View.VISIBLE else View.GONE
}
