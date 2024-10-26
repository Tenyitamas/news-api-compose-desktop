package com.tenyitamas.kip.presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.tenyitamas.kip.presentation.detailed.DetailedScreen
import com.tenyitamas.kip.presentation.news.components.SearchBar
import com.tenyitamas.kip.presentation.shared.DarkGray
import com.tenyitamas.kip.presentation.shared.NewsItem
import com.tenyitamas.kip.presentation.shared.Orange
import java.awt.Desktop
import java.net.URI

class NewsScreen : Screen {
    @Composable
    @ExperimentalMaterialApi
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<NewsScreenModel>()
        val state by screenModel.state.collectAsState()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth().background(DarkGray)) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .border(4.dp, Orange, shape = RoundedCornerShape(15.dp))
                            .align(Alignment.Center)
                            .padding(8.dp)
                            .background(DarkGray),
                        hint = "🔍 Search"
                    ) {
                        screenModel.search(it)
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().background(color = DarkGray).padding(paddingValues)
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
                        Column(
                            modifier = Modifier.fillMaxSize().align(Alignment.TopCenter),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(0.6f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                                userScrollEnabled = true
                            ) {
                                this.items(currentState.articles) {
                                    NewsItem(
                                        article = it,
                                        onArticleClick = {
                                            openWebpage(URI.create(it.url ?: "https://en.wikipedia.org/wiki/HTTP_404"))
                                            // navigator.push(DetailedScreen(it))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun openWebpage(uri: URI?): Boolean {
    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            desktop.browse(uri)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return false
}
