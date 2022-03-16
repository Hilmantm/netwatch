package id.kodesumsi.netwatch.core.data.source.local

import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.data.source.local.room.MovieDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LocalDataSourceImpl constructor(
    private val movieDao: MovieDao
): LocalDataSource {

    override fun getAllFavoriteMovies(): Flowable<List<MovieEntity>> {
        return movieDao.getAllFavoriteMovies()
    }

    override fun insertFavoriteMovie(movie: MovieEntity): Completable {
        return movieDao.insertFavoriteMovie(movie)
    }

    override fun removeFavoriteMovie(movie: MovieEntity): Completable {
        return movieDao.removeFavoriteMovie(movie)
    }
}