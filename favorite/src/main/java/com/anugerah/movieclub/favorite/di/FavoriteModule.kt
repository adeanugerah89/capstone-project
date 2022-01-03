package com.anugerah.movieclub.favorite.di

import com.anugerah.movieclub.favorite.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}