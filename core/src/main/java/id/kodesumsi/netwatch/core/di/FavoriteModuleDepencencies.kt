package id.kodesumsi.netwatch.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDepencencies {

    fun movieUseCase(): MovieListUseCase

}