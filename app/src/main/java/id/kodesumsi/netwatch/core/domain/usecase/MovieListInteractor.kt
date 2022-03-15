package id.kodesumsi.netwatch.core.domain.usecase

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class MovieListInteractor @Inject constructor(
    private val movieRepository: MovieRepository
): MovieListUseCase {
    override fun getMovieList(category: String): Flowable<Resource<List<Movie>>> {
        return movieRepository.getMovieList(category)
    }
}