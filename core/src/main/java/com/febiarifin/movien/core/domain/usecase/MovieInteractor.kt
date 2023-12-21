package com.febiarifin.movien.core.domain.usecase

import com.febiarifin.movien.core.domain.model.Movie
import com.febiarifin.movien.core.domain.repository.MovieRepository
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: MovieRepository) : MovieUseCase {

    override fun getTrendingMovies() = movieRepository.getTrendingMovies()

    override fun getMoviesNowPlaying() = movieRepository.getMoviesNowPlaying()

    override fun getPopularMovies() = movieRepository.getPopularMovies()

    override fun getUpcomingMovies() = movieRepository.getUpcomingMovies()

    override fun getTopRatedMovies() = movieRepository.getTopRatedMovies()

    override suspend fun insertMovie(movie: Movie) = movieRepository.insertMovie(movie)
}