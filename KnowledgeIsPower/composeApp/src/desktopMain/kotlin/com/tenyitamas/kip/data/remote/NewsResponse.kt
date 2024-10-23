package com.tenyitamas.kip.data.remote

import com.tenyitamas.kip.domain.model.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
