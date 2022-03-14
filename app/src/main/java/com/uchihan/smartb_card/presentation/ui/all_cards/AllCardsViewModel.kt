package com.uchihan.smartb_card.presentation.ui.all_cards

import androidx.lifecycle.ViewModel

import android.content.SharedPreferences
import android.os.Build
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.domain.use_case.NearbyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCardsViewModel @Inject constructor(
    private val nearbyUseCase: NearbyUseCase
) : ViewModel() {


    suspend fun getAllNearby(): List<RoomUserModel> {
        return nearbyUseCase.getAllUsers()
    }

    suspend fun deleteNearby(roomUserModel: RoomUserModel){
        nearbyUseCase.deleteNearby(roomUserModel)
    }
}