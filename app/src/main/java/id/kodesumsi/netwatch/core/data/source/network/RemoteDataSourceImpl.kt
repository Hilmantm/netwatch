package id.kodesumsi.netwatch.core.data.source.network

import android.util.Log
import id.kodesumsi.netwatch.core.data.source.network.response.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkService: NetworkService
): RemoteDataSource {

    @API_KEY @Inject lateinit var apiKey: String

    override fun getMovieList(category: String): Flowable<ApiResponse<List<Movie>>> {
        val resultData = PublishSubject.create<ApiResponse<List<Movie>>>()

        val client = networkService.getMovieList(apiKey, category)

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

}