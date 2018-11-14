package com.framgia.data.remote.response

import com.framgia.data.model.CastEntity
import com.google.gson.annotations.SerializedName

/**
 * Created: 16/07/2018
 * By: Sang
 * Description:
 */
class CreditResponse {

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("cast")
    val casts: List<CastEntity> = listOf()
}
