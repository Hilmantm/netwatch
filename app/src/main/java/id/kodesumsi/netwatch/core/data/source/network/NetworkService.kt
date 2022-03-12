package id.kodesumsi.netwatch.core.data.source.network

import id.kodesumsi.netwatch.core.data.source.network.response.BaseResponse
import id.kodesumsi.netwatch.core.data.source.network.response.Movie
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/movie/{category}")
    fun getMovieList(
        @Query("api_key") apiKey: String,
    ): Flowable<BaseResponse<List<Movie>>>

}