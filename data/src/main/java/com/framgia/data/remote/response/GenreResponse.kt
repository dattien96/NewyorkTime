package com.framgia.data.remote.response

import com.framgia.data.model.GenreEntity
import com.google.gson.annotations.SerializedName

/**
 * Created: 13/07/2018
 * By: Sang
 * Description:
 */
class GenreResponse {

    @SerializedName("genres")
    var genres: List<GenreEntity> = listOf()
}
