package id.kodesumsi.netwatch.core.domain.usecase

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import io.reactivex.rxjava3.core.Flowable

interface MovieListUseCase {
    fun getMovieList(category: String): Flowable<Resource<List<Movie>>>
//    fun getAllFavoriteMovie(): Flowable<List<Movie>>
//    fun insertFavoriteMovie(movie: Movie)
//    fun removeFavoriteMovie(movie: Movie)
}