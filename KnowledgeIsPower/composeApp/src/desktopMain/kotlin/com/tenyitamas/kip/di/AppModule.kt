package com.tenyitamas.kip.di

import com.tenyitamas.kip.presentation.news.NewsScreenModel
import org.koin.dsl.module

val appModule = module {
    factory { NewsScreenModel() }
}