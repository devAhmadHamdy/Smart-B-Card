package com.uchihan.smartb_card.presentation.ui.home

import androidx.lifecycle.ViewModel

import android.content.SharedPreferences
import android.os.Build
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.domain.use_case.AddCardUseCase
import com.uchihan.smartb_card.domain.use_case.GetAllCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase,private val addCardUseCase: AddCardUseCase
) : ViewModel() {

    private val KEY_UUID = Build.MODEL

    fun getUUID(sharedPreferences: SharedPreferences): String? {

        return sharedPreferences.getString(KEY_UUID, "there is no value")
    }

    suspend fun saveNearby(roomUserModel: RoomUserModel){
        addCardUseCase.addNearby(roomUserModel)
    }
    suspend fun getAllNearby(): List<RoomUserModel> {
        return getAllCardsUseCase.getAllUsers()
    }
}