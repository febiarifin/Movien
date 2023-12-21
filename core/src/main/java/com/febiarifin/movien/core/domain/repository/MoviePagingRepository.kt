package com.febiarifin.movien.core.domain.repository

import androidx.paging.PagingData
import com.febiarifin.movien.core.domain.model.Movie
import com.febiarifin.movien.core.domain.model.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviePagingRepository {
    fun getMoviesPaging(movieType: MovieType): Flow<PagingData<Movie>>

    fun searchMovie(query: StateFlow<String>): Flow<PagingData<Movie>>
}