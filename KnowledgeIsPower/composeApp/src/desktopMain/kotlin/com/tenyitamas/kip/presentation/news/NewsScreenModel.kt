package com.tenyitamas.kip.presentation.news

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import kotlin.coroutines.coroutineContext

class NewsScreenModel: StateScreenModel<NewsScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val id: Int) : State()
    }

    fun swapState() {
        mutableState.value = State.Result(1)
    }

}