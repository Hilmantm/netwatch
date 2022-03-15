package id.kodesumsi.netwatch.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kodesumsi.netwatch.core.data.MovieRepositoryImpl
import id.kodesumsi.netwatch.core.data.source.network.RemoteDataSource
import id.kodesumsi.netwatch.core.domain.repository.MovieRepository
import id.kodesumsi.netwatch.core.domain.usecase.MovieListInteractor
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Injection {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieUseCase(
        movieRepository: MovieRepository
    ): MovieListUseCase {
        return MovieListInteractor(movieRepository)
    }

}