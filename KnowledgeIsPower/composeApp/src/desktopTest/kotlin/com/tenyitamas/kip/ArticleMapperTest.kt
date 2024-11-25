package com.tenyitamas.kip

import com.tenyitamas.kip.data.local.toArticle
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.domain.model.Source
import org.junit.Test

class ArticleMapperTest {

    @Test
    fun mapEntityToModelTest() {
        assert(entity.toArticle() == article)
    }

    @Test
    fun mapEntityToModelTest2() {
        assert(entity.copy(source_id = null, source_name = null).toArticle() == article.copy(source = null))
    }

    private val article = Article(
        id = 0,
        author = "author",
        content = "some content",
        description = "description",
        publishedAt = "2012",
        source = Source(
            id = "1",
            name = "name"
        ),
        title = "title",
        url = null,
        urlToImage = "someurl"
    )

    private var entity = ArticleEntity(
        id = 0,
        author = "author",
        content = "some content",
        description = "description",
        publishedAt = "2012",
        source_id = "1",
        source_name = "name",
        title = "title",
        url = null,
        urlToImage = "someurl"
    )
}
