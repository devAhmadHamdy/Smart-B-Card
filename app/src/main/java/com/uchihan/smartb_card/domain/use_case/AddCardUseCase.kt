package com.uchihan.smartb_card.domain.use_case

import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.repository.NearbyRepoImpl
import com.uchihan.smartb_card.domain.repository.NearbyRepo
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val repository: NearbyRepoImpl) {

    suspend fun addNearby(roomUserModel: RoomUserModel){
        repository.addNearby(roomUserModel)
    }

}