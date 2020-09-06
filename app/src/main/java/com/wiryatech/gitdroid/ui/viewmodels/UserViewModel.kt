package com.wiryatech.gitdroid.ui.viewmodels

import androidx.lifecycle.LiveData
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

    fun searchUser(q: String) = viewModelScope.launch {
        listSearch.postValue(Status.Loading())
        val response = userRepository.searchUser(q)
        listSearch.postValue(handleSearchResponse(response))
    }

    private fun handleSearchResponse(response: Response<Search>) : Status<Search> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Status.Success(result)
            }
        }
        return Status.Error(response.message())
    }
}