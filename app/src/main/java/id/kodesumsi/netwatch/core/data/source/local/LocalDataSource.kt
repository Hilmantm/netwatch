package id.kodesumsi.netwatch.core.data.source.local

import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun getAllFavoriteMovies(): Flowable<List<MovieEntity>>

    fun isFavorite(id: Int): Maybe<MovieEntity>

    fun insertFavoriteMovie(movie: MovieEntity): Completable

    fun removeFavoriteMovie(movie: MovieEntity): Completable

}