package com.uchihan.smartb_card.domain.repository

import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel

interface NearbyRepo {
    suspend fun getAllNearby():List<RoomUserModel>
    suspend fun addNearby(roomUserModel: RoomUserModel)
    suspend fun deleteNearby(roomUserModel: RoomUserModel)
}