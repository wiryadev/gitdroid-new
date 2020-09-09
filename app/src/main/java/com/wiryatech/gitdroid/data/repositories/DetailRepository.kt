package com.wiryatech.gitdroid.data.repositories

import com.wiryatech.gitdroid.data.api.RetrofitBuilder
import com.wiryatech.gitdroid.data.db.UserDb
import com.wiryatech.gitdroid.data.model.User
import retrofit2.Response


class DetailRepository(private val db: UserDb) {

    suspend fun getDetailUser(username: String): Response<User> {
        return RetrofitBuilder.api.getDetailUser(username)
    }

    suspend fun insertFavoriteUser(user: User) = db.getUserDao().insert(user)

    suspend fun deleteUser(user: User) = db.getUserDao().deleteUser(user)

    fun checkFavorite(username: String) = db.getUserDao().checkFavorite(username)

}