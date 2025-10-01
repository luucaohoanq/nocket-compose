/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.di

import android.content.Context
import com.example.nocket.constants.AppwriteConfig
import com.example.nocket.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Functions
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppwriteModule {

    @Provides
    @Singleton
    fun provideAppwriteClient(
        @ApplicationContext context: Context,
    ): Client = Client(context.applicationContext)
        .setSelfSigned(true)
        .setProject(AppwriteConfig.APPWRITE_PROJECT_ID)
        .setEndpoint(AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT)

    @Provides
    @Singleton
    fun provideAccount(client: Client): Account = Account(client)

    @Provides
    @Singleton
    fun provideDatabases(client: Client): Databases = Databases(client)

    @Provides
    @Singleton
    fun provideAppwriteFunctions(
        client: Client,
    ): Functions = Functions(client)

    @Provides
    @Singleton
    fun provideAuthRepository(
        account: Account,
        @ApplicationContext context: Context,
    ): AuthRepository = AuthRepository(account, context)
}
