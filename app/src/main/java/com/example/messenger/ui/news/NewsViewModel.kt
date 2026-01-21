package com.example.messenger.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.model.Message
import com.example.messenger.data.network.NetworkStatusTracker
import com.example.messenger.data.repository.MessageRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    application: Application,
    private val repository: MessageRepository
) : AndroidViewModel(application) {

    private val networkStatusTracker = NetworkStatusTracker(application)
    val isOnline: LiveData<Boolean> = networkStatusTracker.isOnline.asLiveData()

    val messages = repository.messages.asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun refreshMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (isOnline.value != false) {
                    repository.refreshMessages()
                }
            } catch (e: Exception) {
                // Handle error (e.g., show toast via LiveData event)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(message: Message) {
        viewModelScope.launch {
            repository.toggleLike(message)
        }
    }
}

class NewsViewModelFactory(
    private val application: Application,
    private val repository: MessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
