package com.framgia.data.remote.response

import com.framgia.data.base.ModelEntity
import com.google.gson.annotations.SerializedName

data class StoryWrapperResponse<ME : ModelEntity>(
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("last_updated") val lastUpdate: String,
        @SerializedName("num_results") val numResults: String,
        @SerializedName("results") val results: List<ME>)