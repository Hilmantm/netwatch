package id.kodesumsi.netwatch.core.domain.repository

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface MovieRepository {
    fun getMovieList(category: String): Flowable<Resource<List<Movie>>>
    fun searchMovieList(query: String): Flowable<Resource<List<Movie>>>
    fun getMovieDetail(id: Int): Flowable<Resource<Movie>>
    fun isFavorite(id: Int): Maybe<Movie>
    fun getAllFavoriteMovie(): Flowable<List<Movie>>
    fun insertFavoriteMovie(movie: Movie)
    fun removeFavoriteMovie(movie: Movie)
}