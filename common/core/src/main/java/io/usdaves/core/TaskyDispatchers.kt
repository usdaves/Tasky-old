package io.usdaves.core

import kotlinx.coroutines.CoroutineDispatcher

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

data class TaskyDispatchers(
  val Main: CoroutineDispatcher,
  val IO: CoroutineDispatcher,
  val Default: CoroutineDispatcher,
  val Unconfined: CoroutineDispatcher,
)
