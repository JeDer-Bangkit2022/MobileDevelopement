package com.example.jederv1.login

import android.media.session.MediaSession
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jederv1.api.LoginResponse
import com.example.jederv1.api.TokenClass
import com.example.jederv1.entity.UserModel
import com.example.jederv1.entity.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveToken(user: TokenClass) {
        viewModelScope.launch {
            pref.saveToken(user)
        }
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}