package com.anugerah.movieclub.core.data.source

import com.anugerah.movieclub.core.data.source.local.LocalDataSource
import com.anugerah.movieclub.core.data.source.remote.RemoteDataSource
import com.anugerah.movieclub.core.data.source.remote.network.ApiResponse
import com.anugerah.movieclub.core.data.source.remote.response.ListMovieResponse
import com.anugerah.movieclub.core.data.source.remote.response.MovieResponse
import com.anugerah.movieclub.core.domain.model.Movie
import com.anugerah.movieclub.core.domain.repository.IMovieRepository
import com.anugerah.movieclub.core.utils.AppExecutors
import com.anugerah.movieclub.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, ListMovieResponse>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) =
                data == null || data.isEmpty()
//            true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<ListMovieResponse>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: ListMovieResponse) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data.results)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getMovie(id: Int): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovie(id).map {
                    DataMapper.mapMovieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?) = true

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override suspend fun saveCallResult(data: MovieResponse) {
            }
        }.asFlow()

    override fun searchMovie(query: String): Flow<List<Movie>> {
        return localDataSource.searchMovie(query).map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
            .conflate()
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}