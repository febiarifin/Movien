package com.febiarifin.movien.di

import com.febiarifin.movien.core.domain.usecase.MoviePagingUseCase
import com.febiarifin.movien.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SearchModuleDependencies {
    fun provideMovieUseCase(): MovieUseCase

    fun provideMoviePagingUseCase(): MoviePagingUseCase
}