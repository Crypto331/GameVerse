package com.mobbelldev.gameverse.core.di

import com.mobbelldev.gameverse.core.BuildConfig
import com.mobbelldev.gameverse.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var chainRequest = chain.request()
                val requestHeader = chainRequest.url
                    .newBuilder()
                    .addQueryParameter("key", BuildConfig.API_KEY)
                    .build()
                chainRequest = chainRequest.newBuilder().url(requestHeader)
                    .addHeader("Accept", "application/json").build()
                chain.proceed(chainRequest)
            }
            .addInterceptor(if (BuildConfig.DEBUG) HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
}