package com.anugerah.movieclub.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anugerah.movieclub.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@ObsoleteCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    var searchResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUseCase.searchMovie(it)
        }
        .asLiveData()

}