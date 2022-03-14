package id.kodesumsi.netwatch.core.domain.usecase

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import io.reactivex.rxjava3.core.Flowable

interface GetMovieListUseCase {
    fun getMovieList(category: String): Flowable<Resource<List<Movie>>>
}