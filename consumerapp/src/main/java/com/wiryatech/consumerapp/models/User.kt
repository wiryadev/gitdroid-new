package com.wiryatech.consumerapp.models

data class User(
    val login: String,
    val type: String,
    val avatar_url: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)