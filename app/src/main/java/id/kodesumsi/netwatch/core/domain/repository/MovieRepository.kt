package id.kodesumsi.netwatch.core.domain.repository

import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import io.reactivex.rxjava3.core.Flowable

interface MovieRepository {
    fun getMovieList(category: String): Flowable<Resource<List<Movie>>>
}