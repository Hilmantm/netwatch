package id.kodesumsi.netwatch.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase

class FavoriteFragmentViewModel(
    private val movieUseCase: MovieListUseCase
): ViewModel() {

    fun getAllFavoriteMovie() = LiveDataReactiveStreams.fromPublisher(movieUseCase.getAllFavoriteMovie())
    fun insertFavoriteMovie(movie: Movie) = movieUseCase.insertFavoriteMovie(movie)
    fun removeFavoriteMovie(movie: Movie) = movieUseCase.removeFavoriteMovie(movie)

}