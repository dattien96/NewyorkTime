package com.framgia.domain.model

import com.framgia.domain.base.Model

/**
 * Created: 03/08/2018
 * By: Sang
 * Description:
 */
data class MovieInfo(
    val pageCount: Int,
    val movies: List<Movie>
) : Model()
