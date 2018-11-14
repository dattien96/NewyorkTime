package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.Genre
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
data class GenreEntity(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
) : ModelEntity()

class GenreEntityMapper @Inject constructor() : EntityMapper<Genre, GenreEntity> {

    override fun mapToDomain(entity: GenreEntity): Genre =
        Genre(entity.id, entity.name)

    override fun mapToEntity(model: Genre): GenreEntity =
        GenreEntity(model.id, model.name)
}
