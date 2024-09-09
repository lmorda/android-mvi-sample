package com.lmorda.mvisample.data

import com.lmorda.mvisample.domain.RepoDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("/repositories/{id}")
    suspend fun getGithubRepo(
        @Path("id") id: Long
    ): RepoDetailsDto?
}
