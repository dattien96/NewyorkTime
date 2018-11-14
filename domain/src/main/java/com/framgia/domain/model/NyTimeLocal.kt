package com.framgia.domain.model

import com.framgia.domain.base.Model

data class NyTimeLocal(var url: String,
                       var title: String,
                       var type: String = "",
                       var abstract: String,
                       var byline: String,
                       var imageUrl: String,
                       var publishDate: String) : Model()
