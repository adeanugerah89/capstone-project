package com.anugerah.movieclub.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anugerah.movieclub.core.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun movie() = movieUseCase.getAllMovies().asLiveData()
}