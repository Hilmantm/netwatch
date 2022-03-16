package id.kodesumsi.netwatch.core.data

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.data.source.local.LocalDataSource
import id.kodesumsi.netwatch.core.data.source.network.ApiResponse
import id.kodesumsi.netwatch.core.data.source.network.RemoteDataSource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import id.kodesumsi.netwatch.core.utils.AppExecutors
import id.kodesumsi.netwatch.core.utils.DataMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSouce: LocalDataSource,
    private val appExecutors: AppExecutors
): MovieRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun getMovieList(category: String): Flowable<Resource<List<Movie>>> {
        val movies = PublishSubject.create<Resource<List<Movie>>>()

        movies.onNext(Resource.Loading(null))
        val getMovie = remoteDataSource.getMovieList(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                compositeDisposable.dispose()
            }
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
        compositeDisposable.add(getMovie)

        return movies.toFlowable(BackpressureStrategy.BUFFER)
    }

//    override fun getAllFavoriteMovie(): Flowable<List<Movie>> {
//        val result = PublishSubject.create<List<Movie>>()
//
//        val getAllFavorite =  localDataSouce.getAllFavoriteMovies()
//            .observeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete {
//                compositeDisposable.dispose()
//            }
//            .subscribe {
//                result.onNext(DataMapper.mapMovieEntityToDomainMovie(it))
//            }
//        compositeDisposable.add(getAllFavorite)
//
//        return result.toFlowable(BackpressureStrategy.BUFFER)
//    }
//
//    override fun insertFavoriteMovie(movie: Movie) {
//        val movieEntity = DataMapper.movieDomainToMovieEntity(movie)
//        localDataSouce.insertFavoriteMovie(movieEntity)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete {
//                compositeDisposable.dispose()
//            }
//    }
//
//    override fun remoteFavoriteMovie(movie: Movie) {
//        val movieEntity = DataMapper.movieDomainToMovieEntity(movie)
//        localDataSouce.removeFavoriteMovie(movieEntity)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete {
//                compositeDisposable.dispose()
//            }
//    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepositoryImpl(remoteDataSource = remoteData, localDataSouce = localData, appExecutors = appExecutors)
            }
    }
}