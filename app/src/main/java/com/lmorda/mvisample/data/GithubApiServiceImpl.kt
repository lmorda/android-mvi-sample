package com.lmorda.mvisample.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/"

class GithubApiServiceImpl {

    private val apiServiceImpl = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build().create(GithubApiService::class.java)

    suspend fun getGithubRepo(id: Long) = apiServiceImpl.getGithubRepo(id)

}
