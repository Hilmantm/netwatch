package id.kodesumsi.netwatch.core.data.source

import id.kodesumsi.netwatch.core.data.source.network.API_KEY
import id.kodesumsi.netwatch.core.data.source.network.NetworkService
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
): MovieRepository {

    @API_KEY @Inject lateinit var apiKey: String

    override fun getMovieList(category: String) {
        networkService.getMovieList(apiKey = apiKey, category)
    }
}