package com.anugerah.movieclub.core.data.source.remote.network

import com.anugerah.movieclub.core.data.source.remote.response.ListMovieResponse
import com.anugerah.movieclub.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovieList(@Query("api_key") key: String): ListMovieResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") key: String,
        @Query("query") query: String
    ): ListMovieResponse

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): MovieResponse
}