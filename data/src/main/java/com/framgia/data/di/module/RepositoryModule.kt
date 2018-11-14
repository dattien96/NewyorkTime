package com.framgia.data.di.module

import com.framgia.data.repositoryimpl.GenreRepositoryImpl
import com.framgia.data.repositoryimpl.MovieRepositoryImpl
import com.framgia.data.repositoryimpl.PopularRepositoryImp
import com.framgia.data.repositoryimpl.StoryRepositoryImpl
import com.framgia.domain.repository.GenreRepository
import com.framgia.domain.repository.MovieRepository
import com.framgia.domain.repository.PopularRepository
import com.framgia.domain.repository.StoryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providerStoryRepository(repository: StoryRepositoryImpl): StoryRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideGenreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository =
        genreRepositoryImpl

    @Provides
    @Singleton
    fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository =
        movieRepositoryImpl

    @Provides
    @Singleton
    fun providePopularRepository(popularRepositoryImpl: PopularRepositoryImp): PopularRepository =
            popularRepositoryImpl
}
