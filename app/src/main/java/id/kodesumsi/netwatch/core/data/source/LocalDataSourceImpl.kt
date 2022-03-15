package id.kodesumsi.netwatch.core.data.source

import id.kodesumsi.netwatch.core.data.source.local.LocalDataSource
import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.data.source.local.room.MovieDao
import io.reactivex.rxjava3.core.Flowable

class LocalDataSourceImpl constructor(
    private val movieDao: MovieDao
): LocalDataSource {

    override fun getAllFavoriteMovies(): Flowable<List<MovieEntity>> {
        return movieDao.getAllFavoriteMovies()
    }

    override fun insertFavoriteMovie(movie: MovieEntity) {
        movieDao.insertFavoriteMovie(movie)
    }

    override fun removeFavoriteMovie(movie: MovieEntity) {
        movieDao.removeFavoriteMovie(movie)
    }

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSourceImpl(movieDao)
            }
    }
}