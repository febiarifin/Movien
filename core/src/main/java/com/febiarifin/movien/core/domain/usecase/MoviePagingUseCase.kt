package com.febiarifin.movien.core.domain.usecase

import androidx.paging.PagingData
import com.febiarifin.movien.core.domain.model.Movie
import com.febiarifin.movien.core.domain.model.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviePagingUseCase {
    fun getMoviesPaging(movieType: MovieType): Flow<PagingData<Movie>>

    fun searchMoviesPaging(query: StateFlow<String>): Flow<PagingData<Movie>>
}