package com.wiryatech.gitdroid.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wiryatech.gitdroid.data.api.RetrofitBuilder
import com.wiryatech.gitdroid.data.model.Search
import com.wiryatech.gitdroid.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val api = RetrofitBuilder.api
    val status = MutableLiveData<Boolean>()
    val listUser= MutableLiveData<List<User>>()

    fun searchUsers(q: String) {
        status.value = true
        api.searchUsers(q).enqueue(object: Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                    status.value = false
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                status.value = false
                Log.d("onFailure", t.message.toString())
            }
        })
    }

}