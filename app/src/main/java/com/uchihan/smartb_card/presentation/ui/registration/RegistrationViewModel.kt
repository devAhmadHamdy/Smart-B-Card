package com.uchihan.smartb_card.presentation.ui.registration

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.data.models.UserModel
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : ViewModel() {

    init {
        var userModel = gson.fromJson(
            sharedPreferences.getString(Constants.REGISTERED_USER, ""),
            UserModel::class.java
        )
//        Log.d("Current user",userModel.name)
    }

    private var _isValid = MutableLiveData<Boolean>()
    var isValidData: LiveData<Boolean> = _isValid

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun saveUserPrefs(
        name: String,
        mobile: String,
        email: String,
        work: String,
        address: String,
        gender: Int
    ) {
        var userModel = UserModel(name, mobile, email, work, address, gender)
        sharedPreferences.edit().putString(Constants.REGISTERED_USER, gson.toJson(userModel))
            .apply()
    }

    fun getUserPrefs(): UserModel {
        return gson.fromJson(
            sharedPreferences.getString(Constants.REGISTERED_USER, ""),
            UserModel::class.java
        )
    }
}