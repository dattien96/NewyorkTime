package com.framgia.newyorktime.model

import android.os.Parcelable
import com.framgia.domain.model.ProductionCompany
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
data class ProductionCompanyItem(
    val id: Int = 0,
    val logoPath: String,
    val originCountry: String
) : ModelItem(), Parcelable

class ProductionCompanyItemMapper @Inject constructor() :
    ItemMapper<ProductionCompany, ProductionCompanyItem> {

    override fun mapToPresentation(model: ProductionCompany): ProductionCompanyItem =
        ProductionCompanyItem(
            id = model.id,
            logoPath = model.logoPath,
            originCountry = model.originCountry
        )

    override fun mapToDomain(modelItem: ProductionCompanyItem): ProductionCompany =
        ProductionCompany(
            id = modelItem.id,
            logoPath = modelItem.logoPath,
            originCountry = modelItem.originCountry
        )
}
