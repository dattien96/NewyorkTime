package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.ProductionCountry
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
class ProductionCountryEntity(
    val iso31661: String = "",
    val name: String = ""
) : ModelEntity()

class ProductionCountryEntityMapper @Inject constructor() :
    EntityMapper<ProductionCountry, ProductionCountryEntity> {

    override fun mapToDomain(entity: ProductionCountryEntity): ProductionCountry =
        ProductionCountry(entity.iso31661, entity.name)

    override fun mapToEntity(model: ProductionCountry): ProductionCountryEntity =
        ProductionCountryEntity(model.iso31661, model.name)
}
