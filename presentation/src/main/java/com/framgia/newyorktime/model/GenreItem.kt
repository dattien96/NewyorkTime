package com.framgia.newyorktime.model

import android.os.Parcelable
import com.framgia.domain.model.Genre
import com.framgia.newyorktime.base.model.ItemMapper
import com.framgia.newyorktime.base.model.ModelItem
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

/**
 * Created: 02/08/2018
 * By: Sang
 * Description:
 */
@Parcelize
data class GenreItem(val id: Int, val name: String) : ModelItem(), Parcelable

class GenreItemMapper @Inject constructor() : ItemMapper<Genre, GenreItem> {

    override fun mapToPresentation(model: Genre): GenreItem =
        GenreItem(
            id = model.id,
            name = model.name
        )

    override fun mapToDomain(modelItem: GenreItem): Genre =
        Genre(
            id = modelItem.id,
            name = modelItem.name
        )
}
