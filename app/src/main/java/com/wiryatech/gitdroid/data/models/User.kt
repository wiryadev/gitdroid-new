package com.wiryatech.gitdroid.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val login: String,
    val type: String,
    val avatar_url: String,
    var public_repos: Int? = null,
    var followers: Int? = null,
    var following: Int? = null
)