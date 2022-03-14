package id.kodesumsi.netwatch.core.data

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.data.source.network.ApiResponse
import id.kodesumsi.netwatch.core.data.source.network.RemoteDataSource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import id.kodesumsi.netwatch.core.utils.DataMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
): MovieRepository {

    override fun getMovieList(category: String): Flowable<Resource<List<Movie>>> {
        val movies = PublishSubject.create<Resource<List<Movie>>>()

        movies.onNext(Resource.Loading(null))
        remoteDataSource.getMovieList(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        movies.onNext(
                            Resource.Success(
                                data = DataMapper.mapMovieReponseToDomainMovie(
                                    response.data
                                )
                            )
                        )
                    }
                    is ApiResponse.Empty -> {
                        movies.onNext(Resource.Success(data = listOf()))
                    }
                    is ApiResponse.Error -> {
                        movies.onNext(Resource.Error(response.errorMessage))
                    }
                }
            }

        return movies.toFlowable(BackpressureStrategy.BUFFER)
    }
}