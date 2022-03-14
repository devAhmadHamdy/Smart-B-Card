package com.uchihan.smartb_card.presentation.ui.registration

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson

class RegisterViewModelFactory(private val sharedPreferences: SharedPreferences,
                               private val gson: Gson
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(sharedPreferences,gson) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}