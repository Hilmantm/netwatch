package id.kodesumsi.netwatch.core.data.source.network

import id.kodesumsi.netwatch.core.data.source.network.response.BaseResponse
import id.kodesumsi.netwatch.core.data.source.network.response.MovieResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/{category}")
    fun getMovieList(
        @Path("category") category: String,
        @Query("api_key") apiKey: String
    ): Flowable<BaseResponse<List<MovieResponse>>>

    @GET("search/movie")
    fun searchMovieList(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): Flowable<BaseResponse<List<MovieResponse>>>

}