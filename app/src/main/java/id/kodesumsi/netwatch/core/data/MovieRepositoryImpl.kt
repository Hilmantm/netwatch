package id.kodesumsi.netwatch.core.data

import android.util.Log
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
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.SingleSubject
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSouce: LocalDataSource
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

    override fun searchMovieList(query: String): Flowable<Resource<List<Movie>>> {
        val movies = PublishSubject.create<Resource<List<Movie>>>()

        movies.onNext(Resource.Loading(null))
        remoteDataSource.searchMovieList(query)
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

    override fun getMovieDetail(id: Int): Flowable<Resource<Movie>> {
        val movies = PublishSubject.create<Resource<Movie>>()

        movies.onNext(Resource.Loading(null))
        remoteDataSource.getMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        movies.onNext(
                            Resource.Success(
                                data = DataMapper.movieReponseToDomainMovie(response.data)
                            )
                        )
                    }
                    is ApiResponse.Empty -> {
                        movies.onNext(Resource.Success(data = Movie()))
                    }
                    is ApiResponse.Error -> {
                        movies.onNext(Resource.Error(response.errorMessage))
                    }
                }
            }

        return movies.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun isFavorite(id: Int): Maybe<Movie> {
        return localDataSouce.isFavorite(id).map { DataMapper.movieEntityToDomainMovie(it) }
    }

    override fun getAllFavoriteMovie(): Flowable<List<Movie>> {
        val result = PublishSubject.create<List<Movie>>()

        localDataSouce.getAllFavoriteMovies()
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                result.onNext(DataMapper.mapMovieEntityToDomainMovie(it))
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun insertFavoriteMovie(movie: Movie) {
        val movieEntity = DataMapper.movieDomainToMovieEntity(movie)
        localDataSouce.insertFavoriteMovie(movieEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MOVIE REPOSITORY", "insertFavoriteMovie: success")
            }, {
                Log.e("MOVIE REPOSITORY", "insertFavoriteMovie: ${it.message.toString()}", )
            })
    }

    override fun removeFavoriteMovie(movie: Movie) {
        val movieEntity = DataMapper.movieDomainToMovieEntity(movie)
        localDataSouce.removeFavoriteMovie(movieEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MOVIE REPOSITORY", "remoteFavoriteMovie: success")
            }, {
                Log.e("MOVIE REPOSITORY", "remoteFavoriteMovie: ${it.message.toString()}", )
            })
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepositoryImpl(remoteDataSource = remoteData, localDataSouce = localData)
            }
    }
}