package id.kodesumsi.netwatch.core.di

import android.content.Context
import androidx.room.Room
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
import id.kodesumsi.netwatch.core.utils.Constant
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
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
    fun provideDatabase(
        @ApplicationContext ctx: Context,
        @DB_KEY dbKey: String
    ): NetwatchDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(dbKey.toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            ctx,
            NetwatchDatabase::class.java,
            Constant.DATABASE_NAME
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        database: NetwatchDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(database.movieDao())
    }

}