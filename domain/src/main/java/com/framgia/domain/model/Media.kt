package com.framgia.domain.model

import com.framgia.domain.base.Model

data class Media(
        var type: String? = "",
        var caption: String?,
        var media_metadata: List<MediaMetadata>? = mutableListOf()) : Model()
