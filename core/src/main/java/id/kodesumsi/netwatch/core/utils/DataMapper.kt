package id.kodesumsi.netwatch.core.utils

import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.data.source.network.response.MovieResponse
import id.kodesumsi.netwatch.core.domain.model.Movie


object DataMapper {


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
                adult = it.adult
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
            adult = data.adult
        )
    }

    fun mapMovieEntityToDomainMovie(data: List<MovieEntity>): List<Movie> {
        return data.map {
            Movie(
                id = it.id.toInt(),
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate =  it.releaseDate,
                voteAverage = it.voteAverage?.toDouble(),
            )
        }
    }

    fun movieEntityToDomainMovie(data: MovieEntity): Movie {
        return Movie(
            id = data.id.toInt(),
            overview = data.overview,
            title = data.title,
            posterPath = data.posterPath,
            backdropPath = data.backdropPath,
            releaseDate =  data.releaseDate,
            voteAverage = data.voteAverage?.toDouble()
        )
    }

    fun movieDomainToMovieEntity(data: Movie): MovieEntity {
        return MovieEntity(
            id = data.id.toString(),
            overview = data.overview,
            title = data.title,
            posterPath = data.posterPath,
            backdropPath = data.backdropPath,
            releaseDate =  data.releaseDate,
            voteAverage = data.voteAverage.toString()
        )
    }

}