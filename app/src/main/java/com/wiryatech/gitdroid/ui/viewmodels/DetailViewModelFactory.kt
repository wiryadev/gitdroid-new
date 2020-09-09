package com.wiryatech.gitdroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wiryatech.gitdroid.data.repositories.DetailRepository

class DetailViewModelFactory(private val detailRepository: DetailRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(detailRepository) as T

    }

}