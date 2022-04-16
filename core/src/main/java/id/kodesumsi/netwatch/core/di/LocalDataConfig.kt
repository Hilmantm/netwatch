package id.kodesumsi.netwatch.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.kodesumsi.core.BuildConfig
import id.kodesumsi.netwatch.core.data.source.local.LocalDataSource
import id.kodesumsi.netwatch.core.data.source.local.LocalDataSourceImpl
import id.kodesumsi.netwatch.core.data.source.local.room.NetwatchDatabase
import id.kodesumsi.netwatch.core.data.source.network.DB_KEY
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataConfig {

    @Provides
    @Singleton
    @DB_KEY
    fun provideDBKey(): String {
        return BuildConfig.DB_KEY
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        @ApplicationContext ctx: Context,
        @DB_KEY dbKey: String
    ): LocalDataSource {
        val database = NetwatchDatabase.getInstance(ctx, dbKey)
        return LocalDataSourceImpl(database.movieDao())
    }

}