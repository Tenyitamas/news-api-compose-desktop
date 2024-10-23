package com.tenyitamas.kip.presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.tenyitamas.kip.presentation.detailed.DetailedScreen
import com.tenyitamas.kip.presentation.shared.DarkGray
import com.tenyitamas.kip.presentation.shared.NewsItem

class NewsScreen : Screen {
    @Composable
    @ExperimentalMaterialApi
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<NewsScreenModel>()
        val state by screenModel.state.collectAsState()
        Box(
            modifier = Modifier.fillMaxSize().background(color = DarkGray)
        ) {
            when (val currentState = state) {
                NewsScreenModel.State.Error -> {
                    Text("ERROR OMG")
                }

                NewsScreenModel.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is NewsScreenModel.State.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(0.6f).align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        userScrollEnabled = true
                    ) {
                        this.items(currentState.articles) {
                            NewsItem(
                                article = it,
                                onArticleClick = {
                                    navigator.push(DetailedScreen(1))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
