package com.anugerah.movieclub.detail

import androidx.lifecycle.ViewModel
import com.anugerah.movieclub.core.domain.model.Movie
import com.anugerah.movieclub.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newStatus:Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)

}