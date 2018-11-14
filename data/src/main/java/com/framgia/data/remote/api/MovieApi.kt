package com.framgia.data.remote.api

import com.framgia.data.model.MovieEntity
import com.framgia.data.remote.response.BaseListResponse
import com.framgia.data.remote.response.CreditResponse
import com.framgia.data.remote.response.GenreResponse
import com.framgia.data.remote.response.VideoResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
interface MovieApi {

    @GET("movie/now_playing?")
    fun getNowPlayingMovies(@Query(PARAM_PAGE) page: Int): Single<BaseListResponse<MovieEntity>>

    @GET("movie/popular?")
    fun getPopularMovies(@Query(PARAM_PAGE) page: Int): Single<BaseListResponse<MovieEntity>>

    @GET("movie/top_rated?")
    fun getTopRateMovies(@Query(PARAM_PAGE) page: Int): Single<BaseListResponse<MovieEntity>>

    @GET("movie/upcoming?")
    fun getUpcomingMovies(@Query(PARAM_PAGE) page: Int): Single<BaseListResponse<MovieEntity>>

    @GET("genre/movie/list?")
    fun getGenres(): Single<GenreResponse>

    @GET("movie/{movie_id}/credits?")
    fun getCredits(@Path("movie_id") movieId: Int): Single<CreditResponse>

    @GET("search/movie?")
    fun getSearchMovies(
        @Query(PARAM_QUERY) query: String,
        @Query(PARAM_PAGE) page: Int
    ): Observable<BaseListResponse<MovieEntity>>

    @GET("discover/movie?")
    fun getMoviesByGenre(
        @Query(PARAM_GENRE_ID) genreId: String,
        @Query(PARAM_PAGE) page: Int
    ): Single<BaseListResponse<MovieEntity>>

    @GET("movie/{$PARAM_MOVIE_ID}/videos?")
    fun getVideos(@Path(PARAM_MOVIE_ID) movieId: Int): Single<VideoResponse>

    companion object {

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val PARAM_GENRE_ID = "with_genres"
        const val PARAM_MOVIE_ID = "movie_id"
        const val PARAM_PAGE = "page"
        const val PARAM_QUERY = "query"
    }
}
