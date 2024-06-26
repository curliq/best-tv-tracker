package com.free.tvtracker.discover.response

import com.free.tvtracker.search.response.SmallShowApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingShowApiModel(
    @SerialName("page")
    val page: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val results: List<SmallShowApiModel>
)
