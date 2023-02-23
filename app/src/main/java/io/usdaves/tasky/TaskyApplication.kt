package io.usdaves.tasky

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.usdaves.logger.Logger
import javax.inject.Inject

// Created by usdaves(Usmon Abdurakhmanov) on 2/23/2023

@HiltAndroidApp
class TaskyApplication : Application() {

  @Inject
  lateinit var logger: Logger

  override fun onCreate() {
    super.onCreate()
    logger.setup(isDebugMode = BuildConfig.DEBUG)
  }
}
