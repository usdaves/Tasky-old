package io.usdaves.tasky.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.usdaves.core.ActivityContentSetter

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

@Module
@InstallIn(SingletonComponent::class)
object ReleaseModule {

  @Provides
  fun provideActivityContentSetter(): ActivityContentSetter {
    return ActivityContentSetter { activity, view ->
      activity.setContentView(view)
    }
  }
}
