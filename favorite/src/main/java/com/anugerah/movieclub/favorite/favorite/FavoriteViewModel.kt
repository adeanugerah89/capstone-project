package com.anugerah.movieclub.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anugerah.movieclub.core.domain.usecase.MovieUseCase

class FavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getFavoriteMovie().asLiveData()

}