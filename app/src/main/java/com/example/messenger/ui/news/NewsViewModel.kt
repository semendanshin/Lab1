package com.example.messenger.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.repository.MessageRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: MessageRepository) : ViewModel() {

    val messages = repository.messages.asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun refreshMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshMessages()
            } catch (e: Exception) {
                // Handle error (e.g., show toast via LiveData event)
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class NewsViewModelFactory(private val repository: MessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
