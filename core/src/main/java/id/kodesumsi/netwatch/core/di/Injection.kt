package id.kodesumsi.netwatch.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kodesumsi.netwatch.core.data.MovieRepositoryImpl
import id.kodesumsi.netwatch.core.data.source.local.LocalDataSource
import id.kodesumsi.netwatch.core.data.source.network.RemoteDataSource
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import id.kodesumsi.netwatch.core.domain.usecase.MovieListInteractor
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Injection {

    @Provides
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl.getInstance(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieUseCase(
        movieRepository: MovieRepository
    ): MovieListUseCase {
        return MovieListInteractor(movieRepository)
    }

}