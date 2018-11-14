package com.framgia.domain.model

import com.framgia.domain.base.Model

data class Story(var section: String = "",
                 var subsection: String = "",
                 var title: String = "",
                 var abstract: String = "",
                 var url: String = "",
                 var byline: String = "",
                 var itemType: String = "",
                 var updatedDate: String = "",
                 var createdDate: String = "",
                 var publishedDate: String = "",
                 var multimedia: List<MultiMedia> = mutableListOf(),
                 var shortUrl: String = "") : Model()
