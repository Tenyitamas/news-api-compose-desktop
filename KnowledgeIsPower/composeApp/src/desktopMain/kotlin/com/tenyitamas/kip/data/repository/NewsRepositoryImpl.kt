package com.tenyitamas.kip.data.repository

import com.tenyitamas.kip.Database
import com.tenyitamas.kip.data.remote.NewsApi
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.domain.model.Source
import com.tenyitamas.kip.domain.repository.NewsRepository
import com.tenyitamas.kip.domain.repository.Result

class NewsRepositoryImpl(
    private val api: NewsApi,
    private val db: Database
) : NewsRepository {
    override suspend fun searchNews(query: String, page: Int): Result<List<Article>> {
        return try {
            val response = api.searchNews(
                searchQuery = query,
                pageNumber = page
            )

            val result = response.body()
            if (response.isSuccessful && result != null) {
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
            if (response.isSuccessful && result != null) {
                Result.Success(result.articles)
            } else {
                Result.Error("Error while searching for news")
            }
        } catch (e: Exception) {
            Result.Error("Error occurred: ${e.localizedMessage}")
        }
    }

    override suspend fun saveArticle(article: Article) {
        db.articlesQueries.insertArticle(
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            source_id = article.source?.id,
            source_name = article.source?.name,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )
    }

    override suspend fun deleteArticle(id: Long) {
        db.articlesQueries.deleteArticle(id)
    }

    override fun getSavedArticles(): List<Article> {
        return db.articlesQueries.getAllArticles { id, author, content, description, publishedAt, sourceId, sourceName, title, url, urlToImage ->
            Article(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = sourceName?.let { Source(id = sourceId, name = it) },
                title = title,
                url = url,
                urlToImage = urlToImage
            )
        }.executeAsList()
    }
}
