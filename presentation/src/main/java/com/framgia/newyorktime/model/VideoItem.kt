package com.framgia.newyorktime.model

import com.framgia.domain.model.Video
import com.framgia.newyorktime.base.model.ItemMapper
import com.framgia.newyorktime.base.model.ModelItem
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
class VideoItem @Inject constructor(
    var id: String,
    var key: String,
    var name: String,
    var site: String,
    var size: Int,
    var type: String
) : ModelItem()

class VideoItemMapper @Inject constructor() : ItemMapper<Video, VideoItem> {
    override fun mapToPresentation(model: Video): VideoItem =
        VideoItem(
            id = model.id,
            key = model.key,
            name = model.name,
            site = model.site,
            size = model.size,
            type = model.type
        )

    override fun mapToDomain(modelItem: VideoItem): Video =
        Video(
            id = modelItem.id,
            key = modelItem.key,
            name = modelItem.name,
            site = modelItem.site,
            size = modelItem.size,
            type = modelItem.type
        )

}
