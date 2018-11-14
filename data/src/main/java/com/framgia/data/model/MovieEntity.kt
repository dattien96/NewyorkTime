package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.Movie
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
data class MovieEntity(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("imdb_id")
    var imdbId: Int? = 0,
    @SerializedName("adult")
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String? = "",
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("budget")
    var budget: Int = 0,
    @SerializedName("genres")
    var genres: List<GenreEntity>? = listOf(),
    @SerializedName("homepage")
    var homepage: String? = "",
    @SerializedName("original_language")
    var originalLanguage: String = "",
    @SerializedName("original_title")
    var originalTitle: String = "",
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @SerializedName("production_companies")
    var productionCompanyEntities: List<ProductionCompanyEntity>? = listOf(),
    @SerializedName("production_countries")
    var productionCountryEntities: List<ProductionCountryEntity>? = listOf(),
    @SerializedName("release_date")
    var releaseDate: String = "",
    @SerializedName("revenue")
    var revenue: Int = 0,
    @SerializedName("runtime")
    var runtime: Int? = 0,
    @SerializedName("spoken_languages")
    var spokenLanguageEntities: List<SpokenLanguageEntity>? = listOf(),
    @SerializedName("status")
    var status: String? = "Unknown",
    @SerializedName("tagline")
    var tagLine: String? = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("video")
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0
) : ModelEntity()

class MovieEntityMapper @Inject constructor(
    private val genreEntityMapper: GenreEntityMapper,
    private val productionCompanyEntityMapper: ProductionCompanyEntityMapper,
    private val productionCountryEntityMapper: ProductionCountryEntityMapper,
    private val spokenLanguageEntityMapper: SpokenLanguageEntityMapper
) : EntityMapper<Movie, MovieEntity> {

    override fun mapToDomain(entity: MovieEntity): Movie =
        Movie(
            entity.id,
            entity.imdbId,
            entity.adult,
            entity.backdropPath,
            entity.posterPath,
            entity.budget,
            entity.genres?.map { genreEntityMapper.mapToDomain(it) },
            entity.homepage,
            entity.originalLanguage,
            entity.originalTitle,
            entity.overview,
            entity.popularity,
            entity.productionCompanyEntities?.map { productionCompanyEntityMapper.mapToDomain(it) },
            entity.productionCountryEntities?.map { productionCountryEntityMapper.mapToDomain(it) },
            entity.releaseDate,
            entity.revenue,
            entity.runtime,
            entity.spokenLanguageEntities?.map { spokenLanguageEntityMapper.mapToDomain(it) },
            entity.status,
            entity.tagLine,
            entity.title,
            entity.video,
            entity.voteAverage,
            entity.voteCount
        )

    override fun mapToEntity(model: Movie): MovieEntity =
        MovieEntity(
            model.id,
            model.imdbId,
            model.adult,
            model.backdropPath,
            model.posterPath,
            model.budget,
            model.genres?.map { genreEntityMapper.mapToEntity(it) },
            model.homepage,
            model.originalLanguage,
            model.originalTitle,
            model.overview,
            model.popularity,
            model.productionCompanies?.map { productionCompanyEntityMapper.mapToEntity(it) },
            model.productionCountries?.map { productionCountryEntityMapper.mapToEntity(it) },
            model.releaseDate,
            model.revenue,
            model.runtime,
            model.spokenLanguages?.map { spokenLanguageEntityMapper.mapToEntity(it) },
            model.status,
            model.tagLine,
            model.title,
            model.video,
            model.voteAverage,
            model.voteCount
        )
}
