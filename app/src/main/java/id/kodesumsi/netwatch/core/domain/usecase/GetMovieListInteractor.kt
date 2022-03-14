package id.kodesumsi.netwatch.core.domain.usecase

import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieListInteractor @Inject constructor(
    private val movieRepository: MovieRepository
): GetMovieListUseCase {
    override fun getMovieList(category: String) {
        movieRepository.getMovieList(category)
    }
}