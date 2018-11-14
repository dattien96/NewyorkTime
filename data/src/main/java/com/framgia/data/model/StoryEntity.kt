package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.Story
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class StoryEntity(
        @SerializedName("section")
        var section: String = "",
        @SerializedName("subsection")
        var subsection: String = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("abstract")
        var abstract: String = "",
        @SerializedName("url")
        var url: String = "",
        @SerializedName("byline")
        var byline: String = "",
        @SerializedName("item_type")
        var itemType: String = "",
        @SerializedName("updated_date")
        var updatedDate: String = "",
        @SerializedName("created_date")
        var createdDate: String = "",
        @SerializedName("published_date")
        var publishedDate: String = "",
        @SerializedName("multimedia")
        var multimedia: List<MultimediaEntity> = mutableListOf(),
        @SerializedName("short_url")
        var shortUrl: String = ""
) : ModelEntity()

class StoryEntityMapper @Inject constructor(
        private val multimediaEntityMapper: MultimediaEntityMapper
) : EntityMapper<Story, StoryEntity> {

    override fun mapToDomain(entity: StoryEntity): Story = Story(
            section = entity.section,
            subsection = entity.subsection,
            title = entity.title,
            abstract = entity.abstract,
            url = entity.url,
            byline = entity.byline,
            itemType = entity.itemType,
            updatedDate = entity.updatedDate,
            createdDate = entity.createdDate,
            publishedDate = entity.publishedDate,
            multimedia = entity.multimedia.map { multimediaEntityMapper.mapToDomain(it) },
            shortUrl = entity.shortUrl
    )

    override fun mapToEntity(model: Story): StoryEntity = StoryEntity(
            section = model.section,
            subsection = model.subsection,
            title = model.title,
            abstract = model.abstract,
            url = model.url,
            byline = model.byline,
            itemType = model.itemType,
            updatedDate = model.updatedDate,
            createdDate = model.createdDate,
            publishedDate = model.publishedDate,
            multimedia = model.multimedia.map { multimediaEntityMapper.mapToEntity(it) },
            shortUrl = model.shortUrl
    )

}
