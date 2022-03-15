package id.kodesumsi.netwatch.core.data.source.local

import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import io.reactivex.rxjava3.core.Flowable

interface LocalDataSource {

    fun getAllFavoriteMovies(): Flowable<List<MovieEntity>>

    fun insertFavoriteMovie(movie: MovieEntity)

    fun removeFavoriteMovie(movie: MovieEntity)

}