package com.framgia.newyorktime.model

import android.os.Parcelable
import com.framgia.domain.model.ProductionCountry
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
data class ProductionCountryItem(val name: String) : ModelItem(), Parcelable

class ProductionCountryItemMapper @Inject constructor() :
    ItemMapper<ProductionCountry, ProductionCountryItem> {

    override fun mapToPresentation(model: ProductionCountry): ProductionCountryItem =
        ProductionCountryItem(name = model.name)

    override fun mapToDomain(modelItem: ProductionCountryItem): ProductionCountry =
        ProductionCountry(name = modelItem.name)
}
