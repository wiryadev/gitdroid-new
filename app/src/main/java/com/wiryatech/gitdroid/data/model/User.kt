package com.wiryatech.gitdroid.data.model

import com.google.gson.annotations.SerializedName

data class User(

    var login: String,
    var type: String,
    var avatar_url: String,
    var public_repos: Int = 0,
    var followers: Int = 0,
    var following: Int = 0

)