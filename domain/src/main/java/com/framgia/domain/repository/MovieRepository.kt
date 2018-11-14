package com.framgia.domain.repository

import com.framgia.domain.base.Repository
import com.framgia.domain.model.Cast
import com.framgia.domain.model.Movie
import com.framgia.domain.model.MovieInfo
import com.framgia.domain.model.Video
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
interface MovieRepository : Repository {

    // Remote
    fun getNowPlayingMovies(page: Int): Single<MovieInfo>

    fun getPopularMovies(page: Int): Single<List<Movie>>

    fun getTopRateMovies(page: Int): Single<MovieInfo>

    fun getUpcomingMovies(page: Int): Single<List<Movie>>

    fun getCasts(movieId: Int): Single<List<Cast>>

    fun getSearchMovies(query: String, page: Int): Observable<List<Movie>>

    fun getMoviesByGenre(genreId: String, page: Int): Single<List<Movie>>

    fun getVideos(movieId: Int): Single<List<Video>>
}
