package com.markvtls.diploma_aug.di

import android.content.Context
import com.markvtls.diploma_aug.data.repository.TicketsRepositoryImpl
import com.markvtls.diploma_aug.data.repository.UserRepositoryImpl
import com.markvtls.diploma_aug.data.source.local.UserInfoStore
import com.markvtls.diploma_aug.data.source.remote.FireBaseFirestore
import com.markvtls.diploma_aug.domain.repository.ImageRepository
import com.markvtls.diploma_aug.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserInfoStore(@ApplicationContext context: Context) = UserInfoStore(context)

    @Provides
    @Singleton
    fun provideUserRepository(userInfoStore: UserInfoStore): UserRepository = UserRepositoryImpl(userInfoStore)

    @Provides
    @Singleton
    fun provideTicketsRepository(firestore: FireBaseFirestore): ImageRepository = TicketsRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFireBaseFirestore(): FireBaseFirestore = FireBaseFirestore()
}