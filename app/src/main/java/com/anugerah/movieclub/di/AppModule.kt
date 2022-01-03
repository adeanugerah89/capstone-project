package com.anugerah.movieclub.di

import com.anugerah.movieclub.core.domain.usecase.MovieInteractor
import com.anugerah.movieclub.core.domain.usecase.MovieUseCase
import com.anugerah.movieclub.detail.DetailMovieViewModel
import com.anugerah.movieclub.home.HomeViewModel
import com.anugerah.movieclub.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

@FlowPreview
@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}