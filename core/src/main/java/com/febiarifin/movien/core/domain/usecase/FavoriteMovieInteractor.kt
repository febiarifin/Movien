package com.febiarifin.movien.core.domain.usecase

import com.febiarifin.movien.core.domain.repository.MovieRepository
import javax.inject.Inject

class FavoriteMovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : FavoriteMovieUseCase {
    override fun getFavoriteMovies() = movieRepository.getFavoriteMovies()

    override suspend fun updateMovie(movieId: Int, isFavorite: Boolean) = movieRepository.updateMovie(movieId, isFavorite)

    override fun isFavoriteMovie(movieId: Int) = movieRepository.isFavoriteMovie(movieId)
}