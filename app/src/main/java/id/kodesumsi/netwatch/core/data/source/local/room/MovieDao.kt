package id.kodesumsi.netwatch.core.data.source.local.room

import androidx.room.*
import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.utils.Constant.Companion.MOVIE_TABLE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllFavoriteMovies(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun isFavorite(movieId: Int): Maybe<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: MovieEntity): Completable

    @Delete
    fun removeFavoriteMovie(movie: MovieEntity): Completable

}