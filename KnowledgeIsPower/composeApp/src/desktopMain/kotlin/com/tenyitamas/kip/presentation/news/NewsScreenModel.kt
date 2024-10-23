package com.tenyitamas.kip.presentation.news

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.domain.repository.NewsRepository
import com.tenyitamas.kip.domain.repository.Result
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class NewsScreenModel(
    private val repository: NewsRepository
) : StateScreenModel<NewsScreenModel.State>(State.Loading) {
    sealed class State {
        object Error : State()
        object Loading : State()
        data class Success(val articles: List<Article>) : State()
    }

    fun swapState() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            when (val result = repository.getTopNews("us", 1)) {
                is Result.Error -> mutableState.value = State.Error
                is Result.Success -> mutableState.value = State.Success(result.data ?: emptyList())
            }
        }
    }

}