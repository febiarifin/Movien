package com.febiarifin.movien.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.febiarifin.movien.core.domain.usecase.FavoriteMovieUseCase

class FavoriteViewModel(private val favoriteMovieUseCase: FavoriteMovieUseCase) : ViewModel() {

    fun favoriteMovies() = favoriteMovieUseCase.getFavoriteMovies().asLiveData()
}