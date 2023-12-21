package com.febiarifin.movien.core.di

import com.febiarifin.movien.core.data.repository.MoviePagingRepositoryImpl
import com.febiarifin.movien.core.data.repository.MovieRepositoryImpl
import com.febiarifin.movien.core.domain.repository.MoviePagingRepository
import com.febiarifin.movien.core.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun provideMoviePagingRepository(moviePagingRepositoryImpl: MoviePagingRepositoryImpl): MoviePagingRepository
}