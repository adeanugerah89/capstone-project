package com.anugerah.movieclub.core.data.source.remote

import com.anugerah.movieclub.core.BuildConfig
import com.anugerah.movieclub.core.data.source.remote.network.ApiResponse
import com.anugerah.movieclub.core.data.source.remote.network.ApiService
import com.anugerah.movieclub.core.data.source.remote.response.ListMovieResponse
import com.anugerah.movieclub.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<ListMovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovieList(BuildConfig.API_KEY)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovie(id: Int): Flow<ApiResponse<MovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovie(id, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovie(query: String) =
        apiService.searchMovie(BuildConfig.API_KEY, query)
}