package com.framgia.data.base

import com.framgia.domain.base.Model

/**
 * Created: 31/07/2018
 * By: Sang
 * Description:
 */
interface EntityMapper<M : Model, ME : ModelEntity> {

    fun mapToDomain(entity: ME): M

    fun mapToEntity(model: M): ME
}
