package com.tenyitamas.kip.presentation.news

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

    private var job: Job? = null

    init {
        loadNews()
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
                is Result.Success -> mutableState.value = State.Success(result.data ?: emptyList())
            }
        }
    }
}
