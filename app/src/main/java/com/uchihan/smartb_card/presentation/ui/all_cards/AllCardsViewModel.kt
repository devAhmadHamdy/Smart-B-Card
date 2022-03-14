package com.uchihan.smartb_card.presentation.ui.all_cards

import androidx.lifecycle.ViewModel

import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.domain.use_case.DeleteCardUseCase
import com.uchihan.smartb_card.domain.use_case.GetAllCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCardsViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase,private val deleteCardUseCase: DeleteCardUseCase
) : ViewModel() {


    suspend fun getAllNearby(): List<RoomUserModel> {
        return getAllCardsUseCase.getAllUsers()
    }

    suspend fun deleteNearby(roomUserModel: RoomUserModel){
        deleteCardUseCase.deleteNearby(roomUserModel)
    }
}