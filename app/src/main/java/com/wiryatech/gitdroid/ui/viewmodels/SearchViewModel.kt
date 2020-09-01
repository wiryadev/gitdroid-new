package com.wiryatech.gitdroid.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.data.repositories.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val userRepository = UserRepository()
    val status: LiveData<Boolean> = userRepository.status
    val listSearch: LiveData<List<User>> = userRepository.listUser

//    init {}

    fun searchUsers(q: String) {
        viewModelScope.launch {
            userRepository.searchUsers(q)
        }
    }
}