package io.usdaves.auth.inject

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.usdaves.auth.remote.AuthApi
import io.usdaves.auth.remote.FirebaseAuthApi
import io.usdaves.auth.repository.AuthRepository
import io.usdaves.auth.repository.ProdAuthRepository
import io.usdaves.core.preferences.AuthPreferences
import io.usdaves.core.repository.ProfileRepository
import io.usdaves.logger.Logger

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

  @Provides
  fun provideAuthRepository(
    authApi: AuthApi,
    profileRepository: ProfileRepository,
    authPreferences: AuthPreferences,
    logger: Logger,
  ): AuthRepository = ProdAuthRepository(authApi, profileRepository, authPreferences, logger)

  @Provides
  fun provideAuthApi(
    firebaseAuth: FirebaseAuth,
    logger: Logger,
  ): AuthApi = FirebaseAuthApi(firebaseAuth, logger)
}
