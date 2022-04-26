package id.kodesumsi.netwatch.core.data.source.network

import android.util.Log
import id.kodesumsi.netwatch.core.data.source.network.response.MovieResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkService: NetworkService,
    @API_KEY private val apiKey: String
): RemoteDataSource {

    override fun getMovieList(category: String): Flowable<ApiResponse<List<MovieResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()

        val client = networkService.getMovieList(category = category, apiKey = apiKey)

        client.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val movies = response.results
                Log.d("RemoteDataSourceImpl", "getMovieList: ${movies.toString()}")
                resultData.onNext(if (movies!!.isNotEmpty()) ApiResponse.Success(movies) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun searchMovieList(query: String): Flowable<ApiResponse<List<MovieResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()

        val client = networkService.searchMovieList(query = query, apiKey = apiKey)

        client.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val movies = response.results
                resultData.onNext(if (movies!!.isNotEmpty()) ApiResponse.Success(movies) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getMovieDetail(id: Int): Flowable<ApiResponse<MovieResponse>> {
        val resultData = PublishSubject.create<ApiResponse<MovieResponse>>()

        val client = networkService.getMovieDetail(id = id, apiKey = apiKey)

        client.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

}