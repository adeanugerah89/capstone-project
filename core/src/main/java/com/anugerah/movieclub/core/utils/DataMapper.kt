package com.anugerah.movieclub.core.utils

import com.anugerah.movieclub.core.data.source.local.entity.MovieEntity
import com.anugerah.movieclub.core.data.source.remote.response.MovieResponse
import com.anugerah.movieclub.core.domain.model.Movie

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> =
        input.map {
            MovieEntity(
                id = it.id,
                title = it.title ?: "",
                overview = it.overview ?: "",
                backdropPath = it.backdropPath ?: "",
                posterPath = it.posterPath ?: "",
                releaseDate = it.releaseDate ?: "",
                voteAverage = it.voteAverage ?: 0.0,
                isFavorite = false
            )
        }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }

    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        backdropPath = input.backdropPath,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )

    fun mapMovieEntityToDomain(input: MovieEntity) = Movie(
        id = input.id,
        title = input.title,
        overview = input.overview,
        backdropPath = input.backdropPath,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )
}