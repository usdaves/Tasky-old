package io.usdaves.core.inject

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Created by usdaves(Usmon Abdurakhmanov) on 2/22/2023

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {


  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

  @Provides
  @Singleton
  fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}
