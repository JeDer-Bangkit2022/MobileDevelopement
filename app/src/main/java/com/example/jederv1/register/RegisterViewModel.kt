package com.example.jederv1.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jederv1.entity.UserModel
import com.example.jederv1.entity.UserPreference
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}