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

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

  @Provides
  fun provideAuthRepository(
    authApi: AuthApi,
    profileRepository: ProfileRepository,
    authPreferences: AuthPreferences,
  ): AuthRepository = ProdAuthRepository(authApi, profileRepository, authPreferences)

  @Provides
  fun provideAuthApi(firebaseAuth: FirebaseAuth): AuthApi = FirebaseAuthApi(firebaseAuth)
}
