package com.febiarifin.movien.ui.main.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.febiarifin.movien.core.domain.model.MovieType
import com.febiarifin.movien.core.domain.usecase.MoviePagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviePagingUseCase: MoviePagingUseCase
): ViewModel() {

    val movieType = MutableStateFlow(MovieType.NOW_PLAYING)

    @OptIn(ExperimentalCoroutinesApi::class)
    val getMoviesPaging by lazy {
        movieType.flatMapLatest {
            moviePagingUseCase.getMoviesPaging(it)
        }.cachedIn(viewModelScope).asLiveData()
    }
}