package com.tenyitamas.kip.di

import com.tenyitamas.kip.data.remote.NewsApi
import com.tenyitamas.kip.data.repository.NewsRepositoryImpl
import com.tenyitamas.kip.domain.repository.NewsRepository
import com.tenyitamas.kip.presentation.news.NewsScreenModel
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
    factory { provideNewsRepository(get()) }
    factory { NewsScreenModel(get()) }
}

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}


fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()


fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideNewsRepository(
    api: NewsApi
): NewsRepository {
    return NewsRepositoryImpl(api)
}

private const val BASE_URL = "https://newsapi.org"
fun provideService(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
