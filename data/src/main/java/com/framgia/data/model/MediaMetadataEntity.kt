package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.MediaMetadata
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class MediaMetadataEntity(
        @SerializedName("url")
        var url: String? = ""
) : ModelEntity()

class MediaMetadataEntityMapper @Inject constructor() : EntityMapper<MediaMetadata, MediaMetadataEntity> {
        override fun mapToDomain(entity: MediaMetadataEntity): MediaMetadata = MediaMetadata(
                url = entity.url
        )

        override fun mapToEntity(model: MediaMetadata): MediaMetadataEntity = MediaMetadataEntity(
                url = model.url
        )
}
