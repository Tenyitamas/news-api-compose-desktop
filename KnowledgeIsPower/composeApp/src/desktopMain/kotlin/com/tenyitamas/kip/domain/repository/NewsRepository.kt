package com.tenyitamas.kip.domain.repository

import com.tenyitamas.kip.domain.model.Article

interface NewsRepository {
    suspend fun searchNews(
        query: String,
        page: Int
    ): Result<List<Article>>

    suspend fun getTopNews(
        countryCode: String,
        page: Int
    ): Result<List<Article>>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(id: Int)


    // fun getSavedArticles(): Flow<List<Article>>
}