package com.framgia.domain.model

import com.framgia.domain.base.Model

/**
 * Created: 01/08/2018
 * By: Sang
 * Description:
 */
data class Video(
    var id: String = "",
    var iso6391: String = "",
    var iso31661: String = "",
    var key: String = "",
    var name: String = "",
    var site: String = "",
    var size: Int = 0,
    var type: String = ""
) : Model()
