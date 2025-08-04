package com.example.nocket.di

import com.example.nocket.repositories.MainLog
import com.example.nocket.repositories.MainLogImpl
import com.example.nocket.repositories.Store
import com.example.nocket.repositories.StoreImpl
import com.example.nocket.repositories.StoreImpl2
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindMainLog(mainLog: MainLogImpl): MainLog

    @Binds
    @Singleton
    abstract fun bindStore(store: StoreImpl2): Store

}