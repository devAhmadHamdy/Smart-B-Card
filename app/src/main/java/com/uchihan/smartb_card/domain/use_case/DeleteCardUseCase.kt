package com.uchihan.smartb_card.domain.use_case

import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.repository.NearbyRepoImpl
import com.uchihan.smartb_card.domain.repository.NearbyRepo
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(private val repository: NearbyRepoImpl) {

    suspend fun deleteNearby(roomUserModel: RoomUserModel){
        repository.deleteNearby(roomUserModel)
    }
}