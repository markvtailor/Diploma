package com.markvtls.diploma.di

import android.content.Context
import com.markvtls.diploma.data.repository.TicketsRepositoryImpl
import com.markvtls.diploma.data.repository.UserRepositoryImpl
import com.markvtls.diploma.data.source.local.UserInfoStore
import com.markvtls.diploma.data.source.remote.FireBaseFirestore
import com.markvtls.diploma.domain.repository.TicketRepository
import com.markvtls.diploma.domain.repository.UserRepository
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
    fun provideTicketsRepository(firestore: FireBaseFirestore): TicketRepository = TicketsRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFireBaseFirestore(): FireBaseFirestore = FireBaseFirestore()
}