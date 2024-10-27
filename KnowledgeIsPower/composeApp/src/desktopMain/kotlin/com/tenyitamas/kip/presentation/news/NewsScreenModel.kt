package com.tenyitamas.kip.presentation.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.domain.repository.NewsRepository
import com.tenyitamas.kip.domain.repository.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsScreenModel(
    private val repository: NewsRepository
) : StateScreenModel<NewsScreenModel.State>(State.Loading) {
    sealed class State {
        object Error : State()
        object Loading : State()
        data class Success(val articles: List<Article>) : State()
    }

    var savedArticles by mutableStateOf(emptyList<Article>())
        private set

    private var job: Job? = null

    init {
        loadNews()
        loadSavedArticles()
    }

    private fun loadSavedArticles() {
        savedArticles = repository.getSavedArticles()
    }
    private fun loadNews() {
        job?.cancel()

        job = screenModelScope.launch {
            mutableState.value = State.Loading
            when (val result = repository.getTopNews("us", 1)) {
                is Result.Error -> mutableState.value = State.Error
                is Result.Success -> mutableState.value = State.Success(result.data ?: emptyList())
            }
        }
    }

    fun search(keyword: String) {
        if (keyword.isEmpty()) {
            loadNews()
            return
        }
        job?.cancel()
        job = screenModelScope.launch {
            mutableState.value = State.Loading
            when (val result = repository.searchNews(keyword, 1)) {
                is Result.Error -> mutableState.value = State.Error
                is Result.Success -> {

                    mutableState.value = State.Success(result.data ?: emptyList())
                }
            }
        }
    }

    fun onSaveClick(article: Article) {
        job?.cancel()
        job = screenModelScope.launch {
            savedArticles.firstOrNull { it.url != null && it.url == article.url }?.let { saved ->
                repository.deleteArticle(saved.id)
            } ?: run {
                repository.saveArticle(article)
            }

            savedArticles = repository.getSavedArticles()
        }
    }

}
