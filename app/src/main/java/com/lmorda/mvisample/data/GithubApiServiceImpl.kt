package com.lmorda.mvisample.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("/repositories/{id}")
    suspend fun getGithubRepo(
        @Path("id") id: Long
    ): RepoDetailsDto?
}

class GithubApiServiceImpl {
    private val apiServiceImpl = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
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

