package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.Cast
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
data class CastEntity(
    @SerializedName("cast_id")
    val castId: Int = 0,
    @SerializedName("character")
    val character: String = "",
    @SerializedName("credit_id")
    val creditId: String = "",
    @SerializedName("gender")
    val gender: Int? = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("profile_path")
    val profilePath: String? = ""
) : ModelEntity()

class CastEntityMapper @Inject constructor() : EntityMapper<Cast, CastEntity> {
    override fun mapToDomain(entity: CastEntity): Cast =
        Cast(
            entity.castId,
            entity.character,
            entity.creditId,
            entity.gender,
            entity.id,
            entity.name,
            entity.order,
            entity.profilePath
        )

    override fun mapToEntity(model: Cast): CastEntity =
        CastEntity(
            model.castId,
            model.character,
            model.creditId,
            model.gender,
            model.id,
            model.name,
            model.order,
            model.profilePath
        )
}
