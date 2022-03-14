package id.kodesumsi.netwatch.core.utils

import id.kodesumsi.netwatch.core.data.source.network.response.CastResponse
import id.kodesumsi.netwatch.core.data.source.network.response.CreditsResponse
import id.kodesumsi.netwatch.core.data.source.network.response.MovieResponse
import id.kodesumsi.netwatch.core.domain.model.Cast
import id.kodesumsi.netwatch.core.domain.model.Credits
import id.kodesumsi.netwatch.core.domain.model.Movie


object DataMapper {

    fun mapCastResponseToDomainCast(data: List<CastResponse>?): List<Cast> {
        return data!!.map {
            Cast(
                castId = it.castId,
                name = it.name,
                profilePath = it.profilePath,
                id = it.id,
                gender = it.gender,
                originalName = it.originalName,
                character = it.character,
                order = it.order
            )
        }
    }

    fun creditResponseToDomainCredit(data: CreditsResponse): Credits {
        return Credits(
            cast = this.mapCastResponseToDomainCast(data.cast)
        )
    }

    fun mapMovieReponseToDomainMovie(data: List<MovieResponse>): List<Movie> {
        return data.map {
            Movie(
                id = it.id,
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                voteAverage = it.voteAverage,
            )
        }
    }

    fun movieReponseToDomainMovie(data: MovieResponse): Movie {
        return Movie(
            id = data.id,
            backdropPath = data.backdropPath,
            genreIds = data.genreIds,
            originalLanguage = data.originalLanguage,
            originalTitle = data.originalTitle,
            overview = data.overview,
            posterPath = data.posterPath,
            releaseDate = data.releaseDate,
            title = data.title,
            voteAverage = data.voteAverage,
            credits = creditResponseToDomainCredit(data.credits!!)
        )
    }

}