package com.wiryatech.gitdroid.data.model

data class Search(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>,
)