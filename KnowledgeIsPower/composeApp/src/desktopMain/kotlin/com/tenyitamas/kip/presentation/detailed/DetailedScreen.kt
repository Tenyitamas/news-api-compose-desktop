package com.tenyitamas.kip.presentation.detailed

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.tenyitamas.kip.domain.model.Article
import com.tenyitamas.kip.presentation.shared.NewsItem
import java.util.prefs.Preferences

class DetailedScreen(val article: Article) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = {
                    // navigator.pop()
                }
            ) {
                Text(text = "Go back (${article.title})")
            }

            NewsItem(article, onArticleClick = {}, modifier = Modifier.fillMaxWidth()) {
                article.content?.let { Text(it) }
            }
            article.url?.let { url ->

                Button(onClick = {
                    // openWebpage(URI.create(url))
                }) {
                    Text(url)
                }
            }
        }
    }
}
