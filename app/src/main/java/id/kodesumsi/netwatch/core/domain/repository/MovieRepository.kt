package id.kodesumsi.netwatch.core.domain.repository

interface MovieRepository {
    fun getMovieList(category: String)
}