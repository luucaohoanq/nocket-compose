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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppwriteModule {

    @Provides
    @Singleton
    fun provideAppwriteClient(
        @ApplicationContext context: Context
    ): Client {
        return Client(context.applicationContext)
            .setProject(AppwriteConfig.APPWRITE_PROJECT_ID)
            .setEndpoint(AppwriteConfig.APPWRITE_PUBLIC_ENDPOINT)
    }

    @Provides
    @Singleton
    fun provideAccount(client: Client): Account {
        return Account(client)
    }

    @Provides
    @Singleton
    fun provideDatabases(client: Client): Databases {
        return Databases(client)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        account: Account,
        @ApplicationContext context: Context
    ): AuthRepository {
        return AuthRepository(account, context)
    }
}
