package com.framgia.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.NyTimeLocal
import javax.inject.Inject

@Entity(tableName = "nytime")
data class NyTimeLocalEntity(@PrimaryKey(autoGenerate = false)
                             var url: String = "",
                             var title: String = "",
                             var type: String = "",
                             var abstract: String = "",
                             var byline: String = "",
                             var imageUrl: String = "",
                             var publishDate: String = "") : ModelEntity()

class RoomEntityMapper @Inject constructor() : EntityMapper<NyTimeLocal, NyTimeLocalEntity> {
    override fun mapToDomain(entity: NyTimeLocalEntity): NyTimeLocal = NyTimeLocal(
            url = entity.url,
            title = entity.title,
            type = entity.type,
            abstract = entity.abstract,
            byline = entity.byline,
            imageUrl = entity.imageUrl,
            publishDate = entity.publishDate
    )

    override fun mapToEntity(model: NyTimeLocal): NyTimeLocalEntity = NyTimeLocalEntity(
            url = model.url,
            title = model.title,
            type = model.type,
            abstract = model.abstract,
            byline = model.byline,
            imageUrl = model.imageUrl,
            publishDate = model.publishDate
    )

}
