package io.usdaves.test

import io.usdaves.logger.Logger

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

class FakeLogger : Logger {
  override fun setup(isDebugMode: Boolean) = Unit
  override fun v(message: String, vararg args: Any?) = Unit
  override fun v(t: Throwable, message: String, vararg args: Any?) = Unit
  override fun v(t: Throwable) = Unit
  override fun d(message: String, vararg args: Any?) = Unit
  override fun d(t: Throwable, message: String, vararg args: Any?) = Unit
  override fun d(t: Throwable) = Unit
  override fun i(message: String, vararg args: Any?) = Unit
  override fun i(t: Throwable, message: String, vararg args: Any?) = Unit
  override fun i(t: Throwable) = Unit
  override fun w(message: String, vararg args: Any?) = Unit
  override fun w(t: Throwable, message: String, vararg args: Any?) = Unit
  override fun w(t: Throwable) = Unit
  override fun e(message: String, vararg args: Any?) = Unit
  override fun e(t: Throwable, message: String, vararg args: Any?) = Unit
  override fun e(t: Throwable) = Unit
  override fun wtf(message: String, vararg args: Any?) = Unit
  override fun wtf(t: Throwable, message: String, vararg args: Any?) = Unit
  override fun wtf(t: Throwable) = Unit
}
