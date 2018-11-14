package com.framgia.domain.repository

import com.framgia.domain.model.MostPopular
import com.framgia.domain.model.NyTimeLocal
import io.reactivex.Single

interface PopularRepository {
    fun getMostViewPopular() : Single<List<MostPopular>>

    fun savePopulars(items: List<NyTimeLocal>)

    fun unSavePopulars(items: List<NyTimeLocal>)

    fun findPopularExist(url: String): Single<NyTimeLocal>
}
