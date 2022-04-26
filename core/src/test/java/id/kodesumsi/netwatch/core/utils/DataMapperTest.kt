package id.kodesumsi.netwatch.core.utils

import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import id.kodesumsi.netwatch.core.data.source.network.response.MovieResponse
import id.kodesumsi.netwatch.core.domain.model.Movie
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataMapperTest : TestCase() {

    @Test
    fun movieDomainToMovieEntity() {
        val movieDomain = Movie(
            id = 20,
            overview = "Overview Movie",
            title = "Movie title",
            posterPath = "https://posterpath.com/blablabla",
            backdropPath = "https://backdroppath.com/blablabla",
            releaseDate = "01-01-2002",
            voteAverage = 8.5
        )

        val movieEntity = DataMapper.movieDomainToMovieEntity(movieDomain)

        assertNotNull(movieEntity)

        assertEquals(movieDomain.id.toString(), movieEntity.id)
        assertEquals(movieDomain.overview, movieEntity.overview)
        assertEquals(movieDomain.title, movieEntity.title)
        assertEquals(movieDomain.posterPath, movieEntity.posterPath)
        assertEquals(movieDomain.backdropPath, movieEntity.backdropPath)
        assertEquals(movieDomain.releaseDate, movieEntity.releaseDate)
        assertEquals(movieDomain.voteAverage.toString(), movieEntity.voteAverage)
    }

    @Test
    fun movieEntityToDomainMovie() {
        val movieEntity = MovieEntity(
            id = 20.toString(),
            overview = "Overview Movie",
            title = "Movie title",
            posterPath = "https://posterpath.com/blablabla",
            backdropPath = "https://backdroppath.com/blablabla",
            releaseDate = "01-01-2002",
            voteAverage = 8.5.toString()
        )

        val movieDomain = DataMapper.movieEntityToDomainMovie(movieEntity)

        assertNotNull(movieDomain)

        assertEquals(movieEntity.id.toInt(), movieDomain.id)
        assertEquals(movieEntity.overview, movieDomain.overview)
        assertEquals(movieEntity.title, movieDomain.title)
        assertEquals(movieEntity.posterPath, movieDomain.posterPath)
        assertEquals(movieEntity.backdropPath, movieDomain.backdropPath)
        assertEquals(movieEntity.releaseDate, movieDomain.releaseDate)
        assertEquals(movieEntity.voteAverage?.toDouble(), movieDomain.voteAverage)
    }

    @Test
    fun mapMovieEntityToDomainMovie() {
        val movieEntity = MovieEntity(
            id = 20.toString(),
            overview = "Overview Movie",
            title = "Movie title",
            posterPath = "https://posterpath.com/blablabla",
            backdropPath = "https://backdroppath.com/blablabla",
            releaseDate = "01-01-2002",
            voteAverage = 8.5.toString()
        )

        val listofMovieEntity = mutableListOf<MovieEntity>()
        listofMovieEntity.add(movieEntity)
        listofMovieEntity.add(movieEntity)
        listofMovieEntity.add(movieEntity)

        val listOfMovieDomain = DataMapper.mapMovieEntityToDomainMovie(listofMovieEntity)

        assertNotNull(listOfMovieDomain)

        for (movieDomain in listOfMovieDomain) {
            assertEquals(movieEntity.id.toInt(), movieDomain.id)
            assertEquals(movieEntity.overview, movieDomain.overview)
            assertEquals(movieEntity.title, movieDomain.title)
            assertEquals(movieEntity.posterPath, movieDomain.posterPath)
            assertEquals(movieEntity.backdropPath, movieDomain.backdropPath)
            assertEquals(movieEntity.releaseDate, movieDomain.releaseDate)
            assertEquals(movieEntity.voteAverage?.toDouble(), movieDomain.voteAverage)
        }
    }

    @Test
    fun mapMovieResponseToDomainMovie() {
        val movieResponse = MovieResponse(
            id = 20,
            overview = "Overview Movie",
            title = "Movie title",
            posterPath = "https://posterpath.com/blablabla",
            backdropPath = "https://backdroppath.com/blablabla",
            releaseDate = "01-01-2002",
            voteAverage = 8.5,
            adult = true,
            genreIds = listOf(1, 2, 3),
            originalTitle = "Movie original title",
            originalLanguage = "Indonesia"
        )

        val listOfMovieResponse = mutableListOf<MovieResponse>()
        listOfMovieResponse.add(movieResponse)
        listOfMovieResponse.add(movieResponse)
        listOfMovieResponse.add(movieResponse)

        val listOfMovieDomain = DataMapper.mapMovieReponseToDomainMovie(listOfMovieResponse)

        assertNotNull(listOfMovieDomain)

        for (movieDomain in listOfMovieDomain) {
            assertEquals(movieResponse.id, movieDomain.id)
            assertEquals(movieResponse.overview, movieDomain.overview)
            assertEquals(movieResponse.title, movieDomain.title)
            assertEquals(movieResponse.posterPath, movieDomain.posterPath)
            assertEquals(movieResponse.backdropPath, movieDomain.backdropPath)
            assertEquals(movieResponse.releaseDate, movieDomain.releaseDate)
            assertEquals(movieResponse.voteAverage, movieDomain.voteAverage)
            assertEquals(movieResponse.adult, movieDomain.adult)
            assertEquals(movieResponse.genreIds, movieDomain.genreIds)
            assertEquals(movieResponse.originalTitle, movieDomain.originalTitle)
            assertEquals(movieResponse.originalLanguage, movieDomain.originalLanguage)
        }
    }

    @Test
    fun movieResponseToDomainMovie() {
        val movieResponse = MovieResponse(
            id = 20,
            overview = "Overview Movie",
            title = "Movie title",
            posterPath = "https://posterpath.com/blablabla",
            backdropPath = "https://backdroppath.com/blablabla",
            releaseDate = "01-01-2002",
            voteAverage = 8.5,
            adult = true,
            genreIds = listOf(1, 2, 3),
            originalTitle = "Movie original title",
            originalLanguage = "Indonesia"
        )

        val movieDomain = DataMapper.movieReponseToDomainMovie(movieResponse)

        assertNotNull(movieDomain)

        assertEquals(movieResponse.id, movieDomain.id)
        assertEquals(movieResponse.overview, movieDomain.overview)
        assertEquals(movieResponse.title, movieDomain.title)
        assertEquals(movieResponse.posterPath, movieDomain.posterPath)
        assertEquals(movieResponse.backdropPath, movieDomain.backdropPath)
        assertEquals(movieResponse.releaseDate, movieDomain.releaseDate)
        assertEquals(movieResponse.voteAverage, movieDomain.voteAverage)
        assertEquals(movieResponse.adult, movieDomain.adult)
        assertEquals(movieResponse.genreIds, movieDomain.genreIds)
        assertEquals(movieResponse.originalTitle, movieDomain.originalTitle)
        assertEquals(movieResponse.originalLanguage, movieDomain.originalLanguage)
    }

}