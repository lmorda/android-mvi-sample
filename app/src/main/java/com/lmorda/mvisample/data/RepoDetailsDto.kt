package com.lmorda.mvisample.data

import kotlin.math.roundToInt

data class RepoDetailsDto(
    val name: String,
    val description: String,
    val stargazers_count: Double,
    val forks: Double,
    val url: String,
    val avatar_url: String,
) {
    override fun toString(): String =
        "$name\n$description\n${stargazers_count.roundToInt()} " +
                "stars\n${forks.roundToInt()} forks\n$url\n"

}