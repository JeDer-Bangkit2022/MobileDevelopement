package com.example.jederv1.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.jederv1.api.TokenClass
import com.example.jederv1.entity.UserModel
import com.example.jederv1.entity.UserPreference

class HistoryViewModel(private val pref: UserPreference) : ViewModel() {

    fun getToken(): LiveData<TokenClass> {
        return pref.getToken().asLiveData()
    }

}