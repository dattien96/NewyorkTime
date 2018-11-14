package com.framgia.newyorktime.model

import android.os.Parcelable
import com.framgia.domain.model.SpokenLanguage
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
class SpokenLanguageItem(val name: String) : ModelItem(), Parcelable

class SpokenLanguageItemMapper @Inject constructor() :
    ItemMapper<SpokenLanguage, SpokenLanguageItem> {

    override fun mapToPresentation(model: SpokenLanguage): SpokenLanguageItem =
        SpokenLanguageItem(name = model.name)

    override fun mapToDomain(modelItem: SpokenLanguageItem): SpokenLanguage =
        SpokenLanguage(name = modelItem.name)
}
