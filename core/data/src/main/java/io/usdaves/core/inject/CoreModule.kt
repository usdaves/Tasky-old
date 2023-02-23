package io.usdaves.core.inject

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.usdaves.core.TaskyDispatchers
import io.usdaves.core.matcher.AndroidEmailMatcher
import io.usdaves.core.matcher.EmailMatcher
import io.usdaves.core.remote.FirebaseProfileApi
import io.usdaves.core.remote.ProfileApi
import io.usdaves.logger.Logger
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

  @Provides
  fun provideEmailMatcher(logger: Logger): EmailMatcher = AndroidEmailMatcher(logger)

  @Provides
  internal fun provideProfileApi(firestore: FirebaseFirestore, logger: Logger): ProfileApi {
    return FirebaseProfileApi(firestore, logger)
  }
}