package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.Video
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
class VideoEntity(
    var id: String = "",
    var iso6391: String = "",
    var iso31661: String = "",
    var key: String = "",
    var name: String = "",
    var site: String = "",
    var size: Int = 0,
    var type: String = ""
) : ModelEntity()

class VideoEntityMapper @Inject constructor() : EntityMapper<Video, VideoEntity> {

    override fun mapToDomain(entity: VideoEntity): Video =
        Video(
            entity.id,
            entity.iso6391,
            entity.iso31661,
            entity.key,
            entity.name,
            entity.site,
            entity.size,
            entity.type
        )

    override fun mapToEntity(model: Video): VideoEntity =
        VideoEntity(
            model.id,
            model.iso6391,
            model.iso31661,
            model.key,
            model.name,
            model.site,
            model.size,
            model.type
        )
}
