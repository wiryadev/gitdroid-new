package com.wiryatech.gitdroid.data.api

import com.wiryatech.gitdroid.data.model.Search
import com.wiryatech.gitdroid.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    fun searchUsers(@Query("q") q: String): Call<Search>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): User

}