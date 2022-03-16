package id.kodesumsi.netwatch.core.utils

import androidx.room.ColumnInfo
import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
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

    fun mapMovieEntityToDomainMovie(data: List<MovieEntity>): List<Movie> {
        return data.map {
            Movie(
                id = it.id,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate =  it.releaseDate,
                voteAverage = it.voteAverage
            )
        }
    }

    fun mapMovieDomainToMovieEntity(data: List<Movie>): List<MovieEntity> {
        return data.map {
            MovieEntity(
                id = it.id!!,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate =  it.releaseDate,
                voteAverage = it.voteAverage
            )
        }
    }

    fun movieEntityToDomainMovie(data: MovieEntity): Movie {
        return Movie(
            id = data.id,
            overview = data.overview,
            title = data.title,
            posterPath = data.posterPath,
            backdropPath = data.backdropPath,
            releaseDate =  data.releaseDate,
            voteAverage = data.voteAverage
        )
    }

    fun movieDomainToMovieEntity(data: Movie): MovieEntity {
        return MovieEntity(
            id = data.id!!,
            overview = data.overview,
            title = data.title,
            posterPath = data.posterPath,
            backdropPath = data.backdropPath,
            releaseDate =  data.releaseDate,
            voteAverage = data.voteAverage
        )
    }

}