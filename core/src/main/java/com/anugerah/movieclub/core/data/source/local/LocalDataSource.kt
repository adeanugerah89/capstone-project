package com.anugerah.movieclub.core.data.source.local

import com.anugerah.movieclub.core.data.source.local.entity.MovieEntity
import com.anugerah.movieclub.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun getMovie(id: Int): Flow<MovieEntity> = movieDao.getMovie(id)

    fun searchMovie(query: String): Flow<List<MovieEntity>> = movieDao.searchMovie(query)

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    suspend fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovies(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateMovieFavorite(movie)
    }
}