package com.lmorda.mvisample.data

import com.lmorda.mvisample.domain.RepoDetailsDto
import timber.log.Timber

class DataRepositoryImpl {

    private val apiService = GithubApiServiceImpl()

    suspend fun getGithubRepo(id: Long): Result<RepoDetailsDto> =
        try {
            apiService.getGithubRepo(id)?.let {
                Result.success(it)
            } ?: Result.failure(Throwable())
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
}