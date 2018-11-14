package com.framgia.domain.model

import com.framgia.domain.base.Model

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
class ProductionCompany(
    val id: Int = 0,
    val logoPath: String = "",
    val originCountry: String = ""
) : Model()
