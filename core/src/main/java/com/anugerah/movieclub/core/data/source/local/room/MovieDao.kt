package com.anugerah.movieclub.core.data.source.local.room

import androidx.room.*
import com.anugerah.movieclub.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie where id = :id")
    fun getMovie(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM movie where title like '%' || :query || '%'")
    fun searchMovie(query: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie where isFavorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieList: List<MovieEntity>)

    @Update
    fun updateMovieFavorite(movie: MovieEntity)
}