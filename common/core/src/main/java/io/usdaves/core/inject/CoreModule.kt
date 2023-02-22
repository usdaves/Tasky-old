package io.usdaves.core.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.usdaves.core.TaskyDispatchers
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

  @Provides
  @Singleton
  fun provideTaskyDispatchers(): TaskyDispatchers = TaskyDispatchers(
    Main = Dispatchers.Main,
    IO = Dispatchers.IO,
    Default = Dispatchers.Default,
    Unconfined = Dispatchers.Unconfined,
  )
}
