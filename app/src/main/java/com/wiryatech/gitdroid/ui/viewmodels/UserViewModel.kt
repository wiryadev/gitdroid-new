package com.wiryatech.gitdroid.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryatech.gitdroid.data.model.Search
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.data.repositories.UserRepository
import com.wiryatech.gitdroid.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val listSearch: MutableLiveData<Resource<Search>> = MutableLiveData()
    val detailUser: MutableLiveData<Resource<User>> = MutableLiveData()
    val listFollower: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val listFollowing: MutableLiveData<Resource<List<User>>> = MutableLiveData()

    fun searchUser(q: String) = viewModelScope.launch {
        listSearch.postValue(Resource.Loading())
        val response = userRepository.searchUser(q)
        listSearch.postValue(handleSearchResponse(response))
    }

    fun getDetailUser(username: String) = viewModelScope.launch {
        detailUser.postValue(Resource.Loading())
        val response = userRepository.getDetailUser(username)
        detailUser.postValue(handleDetailUser(response))
    }

    fun getFollower(username: String) = viewModelScope.launch {
        listFollower.postValue(Resource.Loading())
        val response = userRepository.getFollower(username)
        listFollower.postValue(handleFollowResponse(response))
    }

    fun getFollowing(username: String) = viewModelScope.launch {
        listFollowing.postValue(Resource.Loading())
        val response = userRepository.getFollowing(username)
        listFollowing.postValue(handleFollowResponse(response))
    }

    private fun handleSearchResponse(response: Response<Search>) : Resource<Search> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleFollowResponse(response: Response<List<User>>) : Resource<List<User>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleDetailUser(response: Response<User>) : Resource<User> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}