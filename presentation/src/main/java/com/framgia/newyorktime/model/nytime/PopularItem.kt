package com.framgia.newyorktime.model.nytime

import com.framgia.domain.model.MostPopular
import com.framgia.domain.model.NyTimeLocal
import com.framgia.newyorktime.base.model.ItemMapper
import com.framgia.newyorktime.base.model.ModelItem
import javax.inject.Inject

class PopularItem(
        var url: String?,
        var byline: String?,
        var title: String?,
        var abstract: String?,
        var published_date: String?,
        var mediaUrl: String?,
        var isSelect: Boolean = false,
        var isSaved: Boolean = false) : ModelItem()

class PopularItemMapper @Inject constructor() : ItemMapper<MostPopular, PopularItem> {
    override fun mapToPresentation(model: MostPopular): PopularItem = PopularItem(
            url = model.url,
            byline = model.byline,
            title = model.title,
            abstract = model.abstract,
            published_date = model.published_date,
            mediaUrl = model.medias?.get(0)?.media_metadata?.get(0)?.url)

    override fun mapToDomain(modelItem: PopularItem): MostPopular = MostPopular(
            url = modelItem.url,
            byline = modelItem.byline,
            title = modelItem.title,
            abstract = modelItem.abstract,
            published_date = modelItem.published_date
    )
}

class PopularLocalMapper @Inject constructor() : ItemMapper<NyTimeLocal, PopularItem> {
    override fun mapToPresentation(model: NyTimeLocal): PopularItem = PopularItem(
            url = model.url,
            title = model.title,
            abstract = model.abstract,
            byline = model.byline,
            mediaUrl = model.imageUrl,
            published_date = model.publishDate
    )

    override fun mapToDomain(modelItem: PopularItem): NyTimeLocal = NyTimeLocal(
            url = modelItem.url ?: "",
            title = modelItem.title ?: "",
            abstract = modelItem.abstract ?: "",
            byline = modelItem.byline ?: "",
            imageUrl = modelItem.mediaUrl ?: "",
            publishDate = modelItem.published_date ?: ""
    )
}
