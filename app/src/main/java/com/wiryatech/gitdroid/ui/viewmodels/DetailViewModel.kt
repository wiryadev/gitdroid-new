package com.wiryatech.gitdroid.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.data.repositories.DetailRepository
import com.wiryatech.gitdroid.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    val detailUser: MutableLiveData<Resource<User>> = MutableLiveData()

    fun getDetailUser(username: String) = viewModelScope.launch {
        detailUser.postValue(Resource.Loading())
        val response = detailRepository.getDetailUser(username)
        detailUser.postValue(handleDetailUser(response))
    }

    private fun handleDetailUser(response: Response<User>) : Resource<User> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertFavoriteUser(user: User) = viewModelScope.launch {
        detailRepository.insertFavoriteUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        detailRepository.deleteUser(user)
    }

    fun checkFavorite(username: String): Int = detailRepository.checkFavorite(username)
}