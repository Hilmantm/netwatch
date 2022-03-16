package id.kodesumsi.netwatch.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.kodesumsi.netwatch.core.data.source.local.LocalDataSourceImpl
import id.kodesumsi.netwatch.core.data.source.local.LocalDataSource
import id.kodesumsi.netwatch.core.data.source.local.room.NetwatchDatabase
import id.kodesumsi.netwatch.core.utils.AppExecutors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataConfig {

    @Provides
    @Singleton
    fun provideLocalDataSource(
        @ApplicationContext ctx: Context
    ): LocalDataSource {
        //val database = NetwatchDatabase.getInstance(ctx)
//        return LocalDataSourceImpl(database.movieDao())
        return LocalDataSourceImpl()
    }

}