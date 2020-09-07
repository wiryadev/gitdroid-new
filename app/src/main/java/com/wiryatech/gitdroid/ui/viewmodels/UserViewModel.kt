package com.wiryatech.gitdroid.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryatech.gitdroid.data.model.Search
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.data.repositories.UserRepository
import com.wiryatech.gitdroid.utils.Status
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val listSearch: MutableLiveData<Status<Search>> = MutableLiveData()
    val detailUser: MutableLiveData<Status<User>> = MutableLiveData()
    val listFollower: MutableLiveData<Status<List<User>>> = MutableLiveData()
    val listFollowing: MutableLiveData<Status<List<User>>> = MutableLiveData()

    fun searchUser(q: String) = viewModelScope.launch {
        listSearch.postValue(Status.Loading())
        val response = userRepository.searchUser(q)
        listSearch.postValue(handleSearchResponse(response))
    }

    fun getDetailUser(username: String) = viewModelScope.launch {
        detailUser.postValue(Status.Loading())
        val response = userRepository.getDetailUser(username)
        detailUser.postValue(handleDetailUser(response))
    }

    fun getFollower(username: String) = viewModelScope.launch {
        listFollower.postValue(Status.Loading())
        val response = userRepository.getFollower(username)
        listFollower.postValue(handleFollowResponse(response))
    }

    fun getFollowing(username: String) = viewModelScope.launch {
        listFollowing.postValue(Status.Loading())
        val response = userRepository.getFollowing(username)
        listFollowing.postValue(handleFollowResponse(response))
    }

    private fun handleSearchResponse(response: Response<Search>) : Status<Search> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Status.Success(result)
            }
        }
        return Status.Error(response.message())
    }

    private fun handleFollowResponse(response: Response<List<User>>) : Status<List<User>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Status.Success(result)
            }
        }
        return Status.Error(response.message())
    }

    private fun handleDetailUser(response: Response<User>) : Status<User> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Status.Success(result)
            }
        }
        return Status.Error(response.message())
    }
}