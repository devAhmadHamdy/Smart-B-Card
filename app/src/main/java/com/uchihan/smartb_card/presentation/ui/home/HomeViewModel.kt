package com.uchihan.smartb_card.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.text.TextUtils

import android.content.SharedPreferences
import android.os.Build
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.domain.use_case.NearbyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nearbyUseCase: NearbyUseCase
) : ViewModel() {

    private val KEY_UUID = Build.MODEL

    fun getUUID(sharedPreferences: SharedPreferences): String? {

        return sharedPreferences.getString(KEY_UUID, "there is no value")
    }

    suspend fun saveNearby(roomUserModel: RoomUserModel){
        nearbyUseCase.addNearby(roomUserModel)
    }
    suspend fun getAllNearby(): List<RoomUserModel> {
        return nearbyUseCase.getAllUsers()
    }
}