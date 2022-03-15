package id.kodesumsi.netwatch.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.utils.Constant.Companion.MOVIE_TABLE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MovieDao {

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun getAllFavoriteMovies(): Flowable<List<MovieEntity>>

    @Insert
    fun insertFavoriteMovie(movie: MovieEntity): Completable

    @Delete
    fun removeFavoriteMovie(movie: MovieEntity): Completable

}