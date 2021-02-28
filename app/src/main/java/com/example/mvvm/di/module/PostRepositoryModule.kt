package com.example.mvvm.di.module

import com.example.mvvm.data.remote.api.ApiService
import com.example.mvvm.data.repository.DefaultPostRepository
import com.example.mvvm.data.repository.MovieRepository
import com.example.mvvm.data.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class PopularRepositoryModule {

    @Provides
    fun providePostRepository(defaultPostRepository: DefaultPostRepository): PostRepository {
        return defaultPostRepository
    }

    @Provides
    fun provideMovieRepository(apiService: ApiService):MovieRepository{
        return  MovieRepository(apiService)

    }
}