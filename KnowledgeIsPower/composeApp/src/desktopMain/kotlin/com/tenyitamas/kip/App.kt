package com.tenyitamas.kip

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.tenyitamas.kip.di.appModule
import com.tenyitamas.kip.presentation.news.NewsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            Navigator(NewsScreen()) { navigator ->
                FadeTransition(navigator)
            }
        }
    }
}
