package com.tenyitamas.kip.data.repository

import com.tenyitamas.kip.data.remote.NewsApi
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.domain.repository.NewsRepository
import com.tenyitamas.kip.domain.repository.Result

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository{
    override suspend fun searchNews(query: String, page: Int): Result<List<Article>> {
        return try {
            val response = api.searchNews(
                searchQuery = query,
                pageNumber = page
            )

            val result = response.body()
            if(response.isSuccessful && result != null) {
                Result.Success(result.articles)
            } else {
                Result.Error("Error while searching for news with query: $query")
            }
        } catch (e: Exception) {
            Result.Error("Error occurred: ${e.localizedMessage}")
        }
    }

    override suspend fun getTopNews(countryCode: String, page: Int): Result<List<Article>> {
        return try {
            val response = api.getTopNews(
                countryCode = countryCode,
                pageNumber = page
            )

            val result = response.body()
            if(response.isSuccessful && result != null) {
                Result.Success(result.articles)
            } else {
                Result.Error("Error while searching for news")
            }
        } catch (e: Exception) {
            Result.Error("Error occurred: ${e.localizedMessage}")
        }
    }

    override suspend fun saveArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArticle(id: Int) {
        TODO("Not yet implemented")
    }

    // override fun getSavedArticles(): Flow<List<Article>> {
    //     TODO("Not yet implemented")
    // }


}