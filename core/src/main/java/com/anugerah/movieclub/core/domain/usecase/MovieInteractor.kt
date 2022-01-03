package com.anugerah.movieclub.core.domain.usecase

import com.anugerah.movieclub.core.domain.model.Movie
import com.anugerah.movieclub.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovies() = movieRepository.getAllMovies()

    override fun getMovie(id: Int) = movieRepository.getMovie(id)

    override fun searchMovie(query: String) = movieRepository.searchMovie(query)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

}