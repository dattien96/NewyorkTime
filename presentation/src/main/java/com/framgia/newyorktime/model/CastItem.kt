package com.framgia.newyorktime.model

import com.framgia.domain.model.Cast
import com.framgia.newyorktime.base.model.ItemMapper
import com.framgia.newyorktime.base.model.ModelItem
import java.util.*
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
data class CastItem(
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int?,
    val id: Int,
    val name: String,
    val profilePath: String?
) : ModelItem()

class CastItemMapper @Inject constructor() : ItemMapper<Cast, CastItem> {

    override fun mapToPresentation(model: Cast): CastItem =
        CastItem(
            castId = model.castId,
            character = model.character,
            creditId = model.creditId,
            gender = model.gender,
            id = model.id,
            name = model.name,
            profilePath = model.profilePath
        )

    override fun mapToDomain(modelItem: CastItem): Cast =
        Cast(
            castId = modelItem.castId,
            character = modelItem.character,
            creditId = modelItem.creditId,
            gender = modelItem.gender,
            id = modelItem.id,
            name = modelItem.name,
            profilePath = modelItem.profilePath
        )
}
