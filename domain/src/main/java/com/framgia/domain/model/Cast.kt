package com.framgia.domain.model

import com.framgia.domain.base.Model

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
data class Cast(
    val castId: Int = 0,
    val character: String = "",
    val creditId: String = "",
    val gender: Int? = 0,
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val profilePath: String? = ""
) : Model()
