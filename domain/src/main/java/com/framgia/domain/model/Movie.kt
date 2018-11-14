package com.framgia.domain.model

import com.framgia.domain.base.Model

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
data class Movie(
    var id: Int = 0,
    var imdbId: Int? = 0,
    var adult: Boolean = false,
    var backdropPath: String? = "",
    var posterPath: String? = "",
    var budget: Int = 0,
    var genres: List<Genre>? = listOf(),
    var homepage: String? = "",
    var originalLanguage: String = "",
    var originalTitle: String = "",
    var overview: String? = "",
    var popularity: Double = 0.0,
    var productionCompanies: List<ProductionCompany>? = listOf(),
    var productionCountries: List<ProductionCountry>? = listOf(),
    var releaseDate: String = "",
    var revenue: Int = 0,
    var runtime: Int? = 0,
    var spokenLanguages: List<SpokenLanguage>? = listOf(),
    var status: String? = "Unknown",
    var tagLine: String? = "",
    var title: String = "",
    var video: Boolean = false,
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0
) : Model()
