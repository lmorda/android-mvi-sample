package com.lmorda.mvisample.domain

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("login")
    val login: String?,

    @SerializedName("avatar_url")
    val avatarUrl: String?,
)
