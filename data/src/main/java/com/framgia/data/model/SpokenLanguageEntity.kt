package com.framgia.data.model

import com.framgia.data.base.EntityMapper
import com.framgia.data.base.ModelEntity
import com.framgia.domain.model.SpokenLanguage
import javax.inject.Inject

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
class SpokenLanguageEntity(
    val iso6391: String = "",
    val name: String = ""
) : ModelEntity()

class SpokenLanguageEntityMapper @Inject constructor() :
    EntityMapper<SpokenLanguage, SpokenLanguageEntity> {

    override fun mapToDomain(entity: SpokenLanguageEntity): SpokenLanguage =
        SpokenLanguage(entity.iso6391, entity.name)

    override fun mapToEntity(model: SpokenLanguage): SpokenLanguageEntity =
        SpokenLanguageEntity(model.iso6391, model.name)
}
