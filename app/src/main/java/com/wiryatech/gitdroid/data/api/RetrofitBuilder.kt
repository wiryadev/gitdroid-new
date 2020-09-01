package com.wiryatech.gitdroid.data.api

import com.wiryatech.gitdroid.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val reqBuilder = chain
                    .request()
                    .newBuilder()
                    .header("Authorization", "token ${BuildConfig.API_KEY}")
                val req = reqBuilder.build()
                chain.proceed(req)
            }
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GithubApi by lazy {
        retrofit.create(GithubApi::class.java)
    }

}