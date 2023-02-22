package io.usdaves.logger.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.usdaves.logger.Logger
import io.usdaves.logger.internal.TimberDebugLogger
import javax.inject.Singleton

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

  @Provides
  @Singleton
  fun provideLogger(): Logger = TimberDebugLogger()
}
