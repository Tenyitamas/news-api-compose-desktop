package com.tenyitamas.kip.presentation.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
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

class NewsScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<NewsScreenModel>()
        val state by screenModel.state.collectAsState()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val currentState = state) {
                NewsScreenModel.State.Loading -> Button(
                    onClick = {
                        // navigator.push(DetailedScreen(1))
                        screenModel.swapState()
                    }
                ) {
                    Text(text = "Go to detailed screen")
                }

                NewsScreenModel.State.Error -> Text(text = "Error")
                is NewsScreenModel.State.Success -> {
                    Text(text = currentState.author.toString())
                }
            }

        }
    }

}