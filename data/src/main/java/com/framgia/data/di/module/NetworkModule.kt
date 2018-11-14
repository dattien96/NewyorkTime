package com.framgia.data.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.framgia.data.BuildConfig
import com.framgia.data.remote.api.MostPopularApi
import com.framgia.data.remote.api.MovieApi
import com.framgia.data.remote.api.StoryApi
import com.framgia.data.remote.mockapi.MockStoryApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
@Module
class NetworkModule {
    val cacheSize = (10 * 1024 * 1024).toLong()

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    @Provides
    fun provideOkHttpClientBuilder(context: Application): OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .cache(Cache(context.cacheDir, cacheSize))
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        var request = chain.request()
                        request = if (hasNetwork(context)!!) {
                            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                        } else {

                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                        }
                        chain.proceed(request)
                    }

    @Provides
    @Singleton
    @Named(BASE_TOP_STORIES_URL)
    fun provideNyTimeOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient =
            builder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                        .addHeader("api-key", BuildConfig.API_NYTIME_KEY)
                        .build()
                return@addInterceptor chain.proceed(request)
            }.build()

    @Provides
    @Singleton
    @Named(BASE_TOP_STORIES_URL)
    fun provideNyTimeRetrofitBuilder(@Named(BASE_TOP_STORIES_URL) okHttpClient: OkHttpClient): Retrofit.Builder =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Provides
    @Singleton
    @Named(BASE_TOP_STORIES_URL)
    fun provideNyTimeRetrofit(@Named(BASE_TOP_STORIES_URL) builder: Retrofit.Builder): Retrofit =
            builder.baseUrl(BASE_TOP_STORIES_URL).build()

    @Provides
    @Singleton
    fun provideStoryApi(@Named(BASE_TOP_STORIES_URL) retrofit: Retrofit): StoryApi =
            if (BuildConfig.MOCK_DATA) MockStoryApi() else
            retrofit.create(StoryApi::class.java)

    @Provides
    @Singleton
    @Named(MOVIE_DB_NAME)
    fun provideMovieOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient =
            builder.addInterceptor { chain ->
                var request = chain.request()
                val url = request
                        .url()
                        .newBuilder().addQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()

    @Provides
    @Singleton
    @Named(MOVIE_DB_NAME)
    fun provideMovieRetrofitBuilder(@Named(MOVIE_DB_NAME) okHttpClient: OkHttpClient): Retrofit.Builder =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Provides
    @Singleton
    @Named(BASE_MOST_POPULAR_URL)
    fun providePopularRetrofit(@Named(BASE_TOP_STORIES_URL) builder: Retrofit.Builder): Retrofit = builder.baseUrl(BASE_MOST_POPULAR_URL).build()

    @Provides
    @Singleton
    fun providePopularApi(@Named(BASE_MOST_POPULAR_URL) retrofit: Retrofit): MostPopularApi = retrofit.create(MostPopularApi::class.java)


    @Provides
    @Named(MOVIE_DB_NAME)
    fun provideMovieRetrofit(@Named(MOVIE_DB_NAME) builder: Retrofit.Builder): Retrofit =
            builder.baseUrl(MovieApi.BASE_URL).build()

    @Provides
    @Singleton
    fun provideMovieApi(@Named(MOVIE_DB_NAME) retrofit: Retrofit): MovieApi =
            retrofit.create(MovieApi::class.java)

    companion object {

        const val BASE_TOP_STORIES_URL = "https://api.nytimes.com/svc/topstories/v2/"
        const val BASE_MOST_POPULAR_URL = "https://api.nytimes.com/svc/mostpopular/v2/"
        const val CONNECTION_TIMEOUT = 10L
        const val READ_TIMEOUT = 10L
        const val WRITE_TIMEOUT = 10L

        const val MOVIE_DB_NAME = "movie_db"
    }
}
