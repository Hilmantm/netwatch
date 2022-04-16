package id.kodesumsi.netwatch.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.utils.Constant.Companion.DATABASE_NAME
import id.kodesumsi.netwatch.core.utils.Constant.Companion.DATABASE_VERSION
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [MovieEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class NetwatchDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: NetwatchDatabase? = null

        @JvmStatic
        fun getInstance(context: Context, dbKey: String): NetwatchDatabase =
            INSTANCE ?: synchronized(this) {
                val passphrase: ByteArray = SQLiteDatabase.getBytes(dbKey.toCharArray())
                val factory = SupportFactory(passphrase)

                val instance = Room.databaseBuilder(
                    context,
                    NetwatchDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
                INSTANCE = instance
                instance
            }
    }

}