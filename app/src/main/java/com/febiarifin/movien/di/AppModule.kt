package com.febiarifin.movien.di

import com.febiarifin.movien.core.domain.usecase.FavoriteMovieInteractor
import com.febiarifin.movien.core.domain.usecase.FavoriteMovieUseCase
import com.febiarifin.movien.core.domain.usecase.MovieInteractor
import com.febiarifin.movien.core.domain.usecase.MoviePagingInteractor
import com.febiarifin.movien.core.domain.usecase.MoviePagingUseCase
import com.febiarifin.movien.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    @Singleton
    abstract fun provideMoviePagingUseCase(moviePagingInteractor: MoviePagingInteractor): MoviePagingUseCase

    @Binds
    @Singleton
    abstract fun provideFavoriteMovieUseCase(favoriteMovieInteractor: FavoriteMovieInteractor): FavoriteMovieUseCase
}