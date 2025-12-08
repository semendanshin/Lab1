package com.example.messenger.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _profileName = MutableLiveData<String>()
    val profileName: LiveData<String> = _profileName

    private val _profileStatus = MutableLiveData<String>()
    val profileStatus: LiveData<String> = _profileStatus

    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        Log.d(TAG, "ViewModel created")
        _profileName.value = "Иван Иванов"
        _profileStatus.value = "Android разработчик"
        _isDarkTheme.value = false
    }

    fun updateProfileName(name: String) {
        _profileName.value = name
    }

    fun updateProfileStatus(status: String) {
        _profileStatus.value = status
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
