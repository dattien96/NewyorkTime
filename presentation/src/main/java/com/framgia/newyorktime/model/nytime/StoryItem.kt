package com.framgia.newyorktime.model.nytime

import android.os.Parcelable
import com.framgia.domain.model.NyTimeLocal
import com.framgia.domain.model.Story
import com.framgia.newyorktime.base.model.ItemMapper
import com.framgia.newyorktime.base.model.ModelItem
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class StoryItem(val title: String,
                     val abstract: String,
                     val url: String,
                     val byline: String,
                     val imageUrl: String,
                     val publishDate: String,
                     var isSelect: Boolean = false,
                     var isSaved: Boolean = false) : ModelItem(), Parcelable

class StoryItemMapper @Inject constructor() : ItemMapper<Story, StoryItem> {

    override fun mapToDomain(item: StoryItem): Story = Story()

    override fun mapToPresentation(model: Story): StoryItem = StoryItem(
            title = model.title,
            abstract = model.abstract,
            url = model.url,
            byline = model.byline,
            imageUrl = if (model.multimedia.isNotEmpty()) model.multimedia[model.multimedia.size - 1].url else "",
            publishDate = model.publishedDate)
}

class StoryLocalMapper @Inject constructor() : ItemMapper<NyTimeLocal, StoryItem> {
    override fun mapToPresentation(model: NyTimeLocal): StoryItem = StoryItem(
            url = model.url,
            title = model.title,
            abstract = model.abstract,
            byline = model.byline,
            imageUrl = model.imageUrl,
            publishDate = model.publishDate
    )

    override fun mapToDomain(modelItem: StoryItem): NyTimeLocal = NyTimeLocal(
            url = modelItem.url,
            title = modelItem.title,
            abstract = modelItem.abstract,
            byline = modelItem.byline,
            imageUrl = modelItem.imageUrl,
            publishDate = modelItem.publishDate
    )

}
