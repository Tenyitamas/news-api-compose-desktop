package com.tenyitamas.kip.data.local

import com.tenyitamas.kip.ArticleEntity
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.domain.model.Source

fun ArticleEntity.toArticle() = Article(
    id = id,
    author = author,
    content = content,
    description = description,
    source = source_name?.let { Source(source_id, source_name) },
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage
)
