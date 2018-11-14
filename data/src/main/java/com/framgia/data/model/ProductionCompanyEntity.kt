package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.ProductionCompany
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
class ProductionCompanyEntity(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("logo_path")
    val logoPath: String = "",
    @SerializedName("origin_country")
    val originCountry: String = ""
) : ModelEntity()

class ProductionCompanyEntityMapper @Inject constructor() :
    EntityMapper<ProductionCompany, ProductionCompanyEntity> {

    override fun mapToDomain(entity: ProductionCompanyEntity): ProductionCompany =
        ProductionCompany(entity.id, entity.logoPath, entity.originCountry)

    override fun mapToEntity(model: ProductionCompany): ProductionCompanyEntity =
        ProductionCompanyEntity(
            model.id,
            model.logoPath,
            model.originCountry
        )
}
