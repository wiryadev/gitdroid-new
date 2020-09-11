package com.wiryatech.gitdroid.data.api

import com.wiryatech.gitdroid.data.models.Search
import com.wiryatech.gitdroid.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun searchUser(@Query("q") q: String): Response<Search>

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): Response<User>

    @GET("users/{username}/followers")
    suspend fun getFollower(@Path("username") username: String): Response<List<User>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<User>>

}