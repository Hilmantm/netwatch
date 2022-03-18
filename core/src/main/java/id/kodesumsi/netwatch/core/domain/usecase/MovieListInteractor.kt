package id.kodesumsi.netwatch.core.domain.usecase

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

class MovieListInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
): MovieListUseCase {
    override fun getMovieList(category: String): Flowable<Resource<List<Movie>>> {
        return movieRepository.getMovieList(category)
    }

    override fun searchMovieList(query: String): Flowable<Resource<List<Movie>>> {
        return movieRepository.searchMovieList(query)
    }

    override fun getMovieDetail(id: Int): Flowable<Resource<Movie>> {
        return movieRepository.getMovieDetail(id)
    }

    override fun isFavorite(id: Int): Maybe<Movie> {
        return movieRepository.isFavorite(id)
    }

    override fun getAllFavoriteMovie(): Flowable<List<Movie>> {
        return movieRepository.getAllFavoriteMovie()
    }

    override fun insertFavoriteMovie(movie: Movie) {
        movieRepository.insertFavoriteMovie(movie)
    }

    override fun removeFavoriteMovie(movie: Movie) {
        movieRepository.removeFavoriteMovie(movie)
    }
}