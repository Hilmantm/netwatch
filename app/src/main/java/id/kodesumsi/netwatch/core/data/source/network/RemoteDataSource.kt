package id.kodesumsi.netwatch.core.data.source.network

import id.kodesumsi.netwatch.core.data.source.network.response.MovieResponse
import io.reactivex.rxjava3.core.Flowable

interface RemoteDataSource {
    fun getMovieList(category: String): Flowable<ApiResponse<List<MovieResponse>>>
}