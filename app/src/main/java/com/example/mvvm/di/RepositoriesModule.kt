package com.example.mvvm.di

import com.example.mvvm.repositories.MainLog
import com.example.mvvm.repositories.MainLogImpl
import com.example.mvvm.repositories.Store
import com.example.mvvm.repositories.StoreImpl
import com.example.mvvm.repositories.StoreImpl2
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