package com.framgia.domain.repository

import com.framgia.domain.base.Repository
import com.framgia.domain.model.Genre
import io.reactivex.Single

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
interface GenreRepository : Repository {

    fun getGenres(): Single<List<Genre>>
}
