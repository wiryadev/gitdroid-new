package com.wiryatech.gitdroid.data.repositories

import com.wiryatech.gitdroid.data.api.RetrofitBuilder
import com.wiryatech.gitdroid.data.db.UserDb
import com.wiryatech.gitdroid.data.model.Search
import retrofit2.Response

class UserRepository(val db: UserDb) {

    suspend fun searchUser(q: String): Response<Search> {
        return RetrofitBuilder.api.searchUser(q)
    }

}