package com.anugerah.movieclub.core.domain.usecase

import com.anugerah.movieclub.core.data.source.Resource
import com.anugerah.movieclub.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getMovie(id: Int): Flow<Resource<Movie>>
    fun searchMovie(query: String): Flow<List<Movie>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}