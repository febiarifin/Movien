package com.febiarifin.movien.core.domain.usecase

import com.febiarifin.movien.core.data.repository.Resource
import com.febiarifin.movien.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getTrendingMovies(): Flow<Resource<List<Movie>>>

    fun getMoviesNowPlaying(): Flow<Resource<List<Movie>>>

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    fun getUpcomingMovies(): Flow<Resource<List<Movie>>>

    fun getTopRatedMovies(): Flow<Resource<List<Movie>>>

    suspend fun insertMovie(movie: Movie)
}