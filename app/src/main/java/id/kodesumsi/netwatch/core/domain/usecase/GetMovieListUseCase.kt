package id.kodesumsi.netwatch.core.domain.usecase

import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import javax.inject.Inject

interface GetMovieListUseCase {
    fun getMovieList(category: String)
}