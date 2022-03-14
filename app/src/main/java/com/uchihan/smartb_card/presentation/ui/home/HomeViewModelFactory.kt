package com.uchihan.smartb_card.presentation.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.uchihan.smartb_card.domain.use_case.NearbyUseCase

class HomeViewModelFactory(private val nearbyUseCase: NearbyUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(nearbyUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}