package io.usdaves.core.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

fun <T> Fragment.collectLatest(flow: Flow<T>, collector: suspend (T) -> Unit) {
  viewLifecycleOwner.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
      flow.collectLatest(collector)
    }
  }
}

fun <T> AppCompatActivity.collectLatest(flow: Flow<T>, collector: suspend (T) -> Unit) {
  lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
      flow.collectLatest(collector)
    }
  }
}
