package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.MultiMedia
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class MultimediaEntity(
        @SerializedName("url")
        var url: String,
        @SerializedName("caption")
        var caption: String) : ModelEntity()

class MultimediaEntityMapper @Inject constructor(): EntityMapper<MultiMedia, MultimediaEntity> {
    override fun mapToDomain(entity: MultimediaEntity): MultiMedia = MultiMedia(
            url = entity.url,
            caption = entity.caption
    )

    override fun mapToEntity(model: MultiMedia): MultimediaEntity = MultimediaEntity(
            url = model.url,
            caption = model.caption
    )
}
