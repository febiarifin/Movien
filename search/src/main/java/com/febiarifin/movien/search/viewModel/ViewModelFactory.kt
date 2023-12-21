package com.febiarifin.movien.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.febiarifin.movien.core.domain.usecase.MoviePagingUseCase
import com.febiarifin.movien.core.domain.usecase.MovieUseCase
import com.febiarifin.movien.search.SearchViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val moviePagingUseCase: MoviePagingUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(movieUseCase, moviePagingUseCase) as T
            else -> throw IllegalArgumentException("Unknown VieModel class name ${modelClass.name}")
        }
}