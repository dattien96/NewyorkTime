package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.MostPopular
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class MostPopularEntity(
        @SerializedName("url")
        var url: String?,
        @SerializedName("adx_keywords")
        var adx_keywords: String? = "",
        @SerializedName("section")
        var section: String? = "",
        @SerializedName("byline")
        var byline: String?,
        @SerializedName("type")
        var type: String? = "",
        @SerializedName("title")
        var title: String?,
        @SerializedName("abstract")
        var abstract: String?,
        @SerializedName("published_date")
        var published_date: String?,
        @SerializedName("media")
        var medias: List<MediaEntity>?
) : ModelEntity()

class MostPopularMapperEntity @Inject constructor(
        private val mediaMapper: MediaEntityMapper
) : EntityMapper<MostPopular, MostPopularEntity> {
    override fun mapToDomain(entity: MostPopularEntity): MostPopular = MostPopular(
            url = entity.url,
            byline = entity.byline,
            title = entity.title,
            abstract = entity.abstract,
            published_date = entity.published_date,
            medias = entity.medias?.map { mediaMapper.mapToDomain(it) }
    )

    override fun mapToEntity(model: MostPopular): MostPopularEntity = MostPopularEntity(
            url = model.url,
            byline = model.byline,
            title = model.title,
            abstract = model.abstract,
            published_date = model.published_date,
            medias = model.medias?.map { mediaMapper.mapToEntity(it) }
    )
}
